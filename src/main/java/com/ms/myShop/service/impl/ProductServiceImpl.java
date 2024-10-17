package com.ms.myShop.service.impl;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;


    @Cacheable(key = "#root.methodName", cacheNames = "products", unless = "#result.statusCode != 200")
    @Override
    public Response<List<ProductResponse>> getProductList() {
        Response<List<ProductResponse>> response = new Response<>();
        try {
            List<Product> productList = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (productList.isEmpty()) {
                throw new ShopException(ResponseMessages.PRODUCT_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }

            List<ProductResponse> responseList = productList.stream().map(this::convert).toList();
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

    @CacheEvict(value = "products", allEntries = true)
    @Override
    public Response save(MultipartFile file, ProductRequest request) {
        Response response = new Response<>();

        try {
            if (file.getOriginalFilename().contains("..")) {
                throw new ShopException(ResponseMessages.INVALID_FILE_FORMAT.value, ResponseCodes.BAD_REQUEST.value);
            }
            System.err.println("metoda gelir");
            Category category = categoryRepository.findByCategoryIdAndActive(request.getCategory().getCategoryId(), EnumAvailableStatus.ACTIVE.value);
            System.err.println("metodu kecir");
            Image savedImage = imageService.saveImage(file, request);
            if (savedImage != null && category != null) {
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
        return response;
    }


    @Cacheable(key = "#id", cacheNames = "product", unless = "#result.statusCode != 200")
    @Override
    public Response<ProductResponse> getById(Long id) {
        Response<ProductResponse> response = new Response<>();
        try {
            if (id == null) {
                throw new ShopException(ResponseMessages.INVALID_REQUEST_DATA.value, ResponseCodes.BAD_REQUEST.value);
            }
            Product product = productRepository.findByProductIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
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

    private ProductResponse convert(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .brand(product.getBrand())
                .model(product.getModel())
                .price(product.getPrice())
                .manufactureDate(product.getManufactureDate())
                .image(product.getImage())
                .build();
    }
}
