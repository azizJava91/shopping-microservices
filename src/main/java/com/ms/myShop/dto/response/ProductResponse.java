package com.ms.myShop.dto.response;


import com.ms.myShop.entity.Category;
import com.ms.myShop.entity.Image;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductResponse implements Serializable {
    Long productId;
    String name;
    String brand;
    String model;
    LocalDateTime manufactureDate;
    BigDecimal price;
    String category;
    Image image;
}
