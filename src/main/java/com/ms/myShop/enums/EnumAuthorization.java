package com.ms.myShop.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumAuthorization {

    ADMIN("admin"),
    USER("user");

    public final String value;
}
