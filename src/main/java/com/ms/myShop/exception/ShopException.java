package com.ms.myShop.exception;

public class ShopException extends RuntimeException {
    private final Integer code;

    public ShopException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
