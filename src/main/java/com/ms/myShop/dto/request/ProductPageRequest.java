package com.ms.myShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Data
@AllArgsConstructor

public class ProductPageRequest {
    private Integer page;
    private Integer size;

    public Pageable pageable(ProductPageRequest request) {
        Integer page = Objects.nonNull(request.getPage()) ? request.getPage() : this.page;
        Integer size = Objects.nonNull(request.getSize()) ? request.getSize() : this.size;
        return PageRequest.of(page, size);
    }
}
