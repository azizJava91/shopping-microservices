package com.ms.myShop.service.interfaces;

import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.entity.Image;
import com.ms.myShop.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image saveImage( MultipartFile file, ProductRequest productRequest) throws IOException;
}
