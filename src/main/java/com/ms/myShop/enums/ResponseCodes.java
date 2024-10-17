package com.ms.myShop.enums;

import lombok.*;

@AllArgsConstructor
public enum ResponseCodes {
    SUCCESS (200),
    INTERNAL_SERVER_ERROR (500),
    BAD_REQUEST(400),
    NOT_FOUND(404);

    public final Integer value;
}
