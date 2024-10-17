package com.ms.myShop.service.impl;

import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.entity.Image;
import com.ms.myShop.exception.ShopException;
import com.ms.myShop.repository.ImageRepository;
import com.ms.myShop.service.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public Image saveImage(MultipartFile file, ProductRequest productRequest) throws IOException, ShopException {

        String fileType = file.getContentType().split("/")[1];
        Image image = Image.builder()
                .fileName(productRequest.getBrand() + " " + productRequest.getModel() + "." + fileType)
                .fileType(file.getContentType())
                .imageData(file.getBytes())
                .build();
        return imageRepository.save(image);
    }
}

