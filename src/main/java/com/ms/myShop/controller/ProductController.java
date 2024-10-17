package com.ms.myShop.controller;

import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.dto.response.ProductResponse;
import com.ms.myShop.dto.response.Response;
import com.ms.myShop.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/list")
    public Response<List<ProductResponse>> getProductList() {
        return productService.getProductList();
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response save(@RequestPart("file") MultipartFile file, @Valid @RequestPart("product") ProductRequest productRequest) {
        return productService.save(file, productRequest);

    }


    @GetMapping("/getById/{id}")
    public Response<ProductResponse> getById(@PathVariable("id") Long id) {
        return productService.getById(id);

    }
}
