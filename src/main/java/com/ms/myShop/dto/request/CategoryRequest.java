package com.ms.myShop.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CategoryRequest {
    @NotNull(message = "Id bos gonderile bilmez")
    Long categoryId;
    @NotNull(message = "ad bos ola bilmez")
    @NotBlank(message = "adda  bosluqlar ola bilmez ")
    @Size(min = 3, max = 15, message = "ad 3 den  qisa ve 15 den uzun ola bilmez")
    String name;
}
