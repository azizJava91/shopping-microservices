package com.ms.myShop.service.interfaces;

import com.ms.myShop.dto.request.ProductPageRequest;
import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.dto.response.ProductResponse;
import com.ms.myShop.dto.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response<Page<ProductResponse>> getProductPage(ProductPageRequest request);

    Response save(MultipartFile file, ProductRequest request) ;

    Response<ProductResponse> getById(Long productId);

    Response delete(Long productId);

    Response update( MultipartFile file, ProductRequest productRequest);

}
