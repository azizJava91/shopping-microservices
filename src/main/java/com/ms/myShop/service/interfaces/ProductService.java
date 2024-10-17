package com.ms.myShop.service.interfaces;

import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.dto.response.ProductResponse;
import com.ms.myShop.dto.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Response<List<ProductResponse>> getProductList();

    Response save(MultipartFile file, ProductRequest request) ;

    Response<ProductResponse> getById(Long id);
}
