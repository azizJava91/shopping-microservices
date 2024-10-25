package com.ms.myShop.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseMessages {
    SUCCESS("Success"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    INVALID_FILE_FORMAT("Invalid file format"),
    PHOTO_REQUIRED("Photo required"),
    CATEGORY_NOT_FOUND("Category not found"),
    PRODUCT_NOT_FOUND("Product not found"),
    INVALID_REQUEST_DATA("Invalid request data"),
    CATEGORY_HAS_ASSOCIATED_PRODUCT("Category has associated product(s)"),
    USER_NOT_FOUND("User not found"),
    MAIL_IS_IN_USE("Mail is in use"),
    IMAGE_NOT_FOUND("Image not found");


    public  final String value;
}
