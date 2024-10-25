package com.ms.myShop.service.impl;

import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.entity.Image;
import com.ms.myShop.enums.ResponseCodes;
import com.ms.myShop.enums.ResponseMessages;
import com.ms.myShop.exception.ShopException;
import com.ms.myShop.repository.ImageRepository;
import com.ms.myShop.service.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Log4j2
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public Image saveImage(MultipartFile file, ProductRequest productRequest) throws IOException, ShopException {
       checkAttack(file);

        String fileType = file.getContentType().split("/")[1];
        Image image = Image.builder()
                .fileName(productRequest.getBrand() + " " + productRequest.getModel() + "." + fileType)
                .fileType(file.getContentType())
                .imageData(file.getBytes())
                .build();
        return imageRepository.save(image);
    }

    @Override
    public Image updateImage(MultipartFile file, ProductRequest productRequest, Long imageId) throws IOException {
        checkAttack(file);

        Image image = imageRepository.findById(imageId).orElseThrow(() -> new RuntimeException(ResponseMessages.IMAGE_NOT_FOUND.value));
        String fileType = file.getContentType().split("/")[1];
        image.setFileName(productRequest.getBrand() + " " + productRequest.getModel() + "." + fileType);
        image.setFileType(file.getContentType());
        image.setImageData(file.getBytes());

        return imageRepository.save(image);
    }

    public void checkAttack(MultipartFile file) {
        if (file.getOriginalFilename().contains("..")) {
            System.out.println("XETAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            throw new ShopException(ResponseMessages.INVALID_FILE_FORMAT.value, ResponseCodes.BAD_REQUEST.value);

        }
    }

}

