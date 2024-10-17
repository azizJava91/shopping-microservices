package com.ms.myShop.dto.request;

import com.ms.myShop.entity.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductRequest {
    Long productId;
    @NotNull(message = "bos ola bilmez")
    @Size(min = 3, max = 20, message = "ad 3 den  qisa ve 15 den uzun ola bilmez")
    String name;
    @NotNull(message = "bos ola bilmez")
    String brand;
    @NotNull(message = "bos ola bilmez")
    String model;
    LocalDateTime manufactureDate;
    @NotNull(message = "bos ola bilmez")
    BigDecimal price;
    @NotNull(message = "bos ola bilmez")
    Category category;
    Integer active;
}
