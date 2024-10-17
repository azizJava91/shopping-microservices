package com.ms.myShop.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseMessages {
    SUCCESS("Success"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    INVALID_FILE_FORMAT("Invalid file format"),
    CATEGORY_NOT_FOUND("Category not found"),
    PRODUCT_NOT_FOUND("Product not found"),
    INVALID_REQUEST_DATA("Invalid request data");


    public  final String value;
}
