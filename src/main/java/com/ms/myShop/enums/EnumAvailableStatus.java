package com.ms.myShop.enums;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public enum EnumAvailableStatus {
    ACTIVE(1),
    DELETED(0);

    public final Integer value;
}
