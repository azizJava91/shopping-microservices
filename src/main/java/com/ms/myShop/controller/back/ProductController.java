package com.ms.myShop.controller.back;

import com.ms.myShop.dto.request.ProductPageRequest;
import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.dto.response.ProductResponse;
import com.ms.myShop.dto.response.Response;
import com.ms.myShop.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/page")
    public Response<Page<ProductResponse>> getProductPage(@RequestBody(required = false) ProductPageRequest request) {
        log.info("request budur: {}", request);
        return productService.getProductPage(request);
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response save(@RequestPart("file") MultipartFile file, @Valid @RequestPart("productRequest") ProductRequest productRequest) {
        return productService.save(file, productRequest);
    }

    @GetMapping("/getById/{productId}")
    public Response<ProductResponse> getById(@PathVariable("productId") Long id) {
        return productService.getById(id);

    }

    @PutMapping("/delete/{productId}")
    public Response delete(@PathVariable("productId") Long id) {
        return productService.delete(id);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@RequestPart(value = "file", required = false) MultipartFile file, @Valid @RequestPart("productRequest") ProductRequest productRequest) {
        return productService.update(file, productRequest);
    }


}
