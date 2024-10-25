package com.ms.myShop.service.impl;

import com.ms.myShop.dto.request.ProductPageRequest;
import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.dto.response.ProductResponse;

import com.ms.myShop.dto.response.Response;
import com.ms.myShop.entity.Category;
import com.ms.myShop.entity.Image;
import com.ms.myShop.entity.Product;
import com.ms.myShop.enums.EnumAvailableStatus;
import com.ms.myShop.enums.ResponseCodes;
import com.ms.myShop.enums.ResponseMessages;
import com.ms.myShop.exception.ShopException;
import com.ms.myShop.repository.CategoryRepository;
import com.ms.myShop.repository.ProductRepository;
import com.ms.myShop.service.interfaces.ImageService;
import com.ms.myShop.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;
    private final CacheManager cacheManager;

    @Cacheable(key = "#request.page", cacheNames = "products", unless = "#result.statusCode != 200")
    @Override
    public Response<Page<ProductResponse>> getProductPage(ProductPageRequest request) {
        Response<Page<ProductResponse>> response = new Response<>();
        try {
            if (request == null) {
                throw new ShopException(ResponseMessages.INVALID_REQUEST_DATA.value, ResponseCodes.BAD_REQUEST.value);
            }
            System.err.println("axtarildi page: "+(request.getPage()+1));
            Pageable pageable = new ProductPageRequest(request.getPage(), request.getSize()).pageable(request);

            Page<Product> productPage = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value, pageable);
            if (productPage.isEmpty()) {
                throw new ShopException(ResponseMessages.PRODUCT_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            Page<ProductResponse> responseList = productPage.map(this::convert);
            response.setBody(responseList);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);
        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }


    @Override
    @CacheEvict(value = "products", allEntries = true)
    public Response save(MultipartFile file, ProductRequest request) {
        Response response = new Response<>();

        try {
            Category category = categoryRepository.findByCategoryIdAndActive(request.getCategory().getCategoryId(), EnumAvailableStatus.ACTIVE.value);
            if (category == null) {
                throw new ShopException(ResponseMessages.CATEGORY_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            if (file.getBytes().length == 0) {
                throw new ShopException(ResponseMessages.PHOTO_REQUIRED.value, ResponseCodes.BAD_REQUEST.value);
            }
            Image savedImage = imageService.saveImage(file, request);
            if (savedImage != null) {
                Product product = Product.builder()
                        .name(request.getName())
                        .brand(request.getBrand())
                        .model(request.getModel())
                        .price(request.getPrice())
                        .manufactureDate(request.getManufactureDate())
                        .category(category)
                        .image(savedImage)
                        .build();
                productRepository.save(product);
                response.setMessage(ResponseMessages.SUCCESS.value);
                response.setStatusCode(ResponseCodes.SUCCESS.value);

            }
        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        System.err.println(response.getMessage());
        System.err.println(response.getStatusCode());
        return response;
    }


    @Cacheable(key = "#productId", cacheNames = "product", unless = "#result.statusCode!=200")
    @Override
    public Response<ProductResponse> getById(Long productId) {
        System.err.println(productId);
        Response<ProductResponse> response = new Response<>();
        try {
            if (productId == null) {
                throw new ShopException(ResponseMessages.INVALID_REQUEST_DATA.value, ResponseCodes.BAD_REQUEST.value);
            }
            Product product = productRepository.findByProductIdAndActive(productId, EnumAvailableStatus.ACTIVE.value);
            if (product == null) {
                throw new ShopException(ResponseMessages.PRODUCT_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            ProductResponse productResponse = convert(product);
            response.setBody(productResponse);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);

        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }

    @Override

    public Response delete(Long productId) {
        Response response = new Response<>();
        try {
            if (productId == null) {
                throw new ShopException(ResponseMessages.INVALID_REQUEST_DATA.value, ResponseCodes.BAD_REQUEST.value);
            }
            Product product = productRepository.findByProductIdAndActive(productId, EnumAvailableStatus.ACTIVE.value);
            if (product == null) {
                throw new ShopException(ResponseMessages.PRODUCT_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }

            product.setActive(EnumAvailableStatus.DELETED.value);
            productRepository.save(product);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);
            cacheManager.getCache("products").clear();
            cacheManager.getCache("product").evict(product.getProductId());
        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }

    @Override
    @CacheEvict(key = "#productRequest.productId", value ={ "product", "products"} , allEntries = true)
    public Response update(MultipartFile file, ProductRequest productRequest) {
        Response response = new Response<>();
        try {
            Product product = productRepository.findByProductIdAndActive(productRequest.getProductId(), EnumAvailableStatus.ACTIVE.value);

            if (product == null) {
                throw new ShopException(ResponseMessages.PRODUCT_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            Category category = categoryRepository.findByCategoryIdAndActive(productRequest.getCategory().getCategoryId(), EnumAvailableStatus.ACTIVE.value);

            if (category == null) {

                throw new ShopException(ResponseMessages.CATEGORY_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            Image existImage = product.getImage();

            if (existImage == null) {

                throw new ShopException(ResponseMessages.IMAGE_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            Image image = file == null || file.getBytes().length == 0 ? existImage : imageService.updateImage(file, productRequest, existImage.getImageId());

            product.setName(productRequest.getName());
            product.setBrand(productRequest.getBrand());
            product.setModel(productRequest.getModel());
            product.setPrice(productRequest.getPrice());
            product.setCategory(category);
            product.setManufactureDate(productRequest.getManufactureDate());
            product.setImage(image);

            productRepository.save(product);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);

        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            System.out.println(e.getMessage());
            response.setStatusCode(e.getCode());
            System.out.println(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        System.err.println(response.getMessage());
        System.err.println(response.getStatusCode());
        return response;
    }

    private ProductResponse convert(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .brand(product.getBrand())
                .model(product.getModel())
                .price(product.getPrice())
                .manufactureDate(product.getManufactureDate())
                .image(product.getImage())
                .category(product.getCategory().getName())
                .build();
    }
}
