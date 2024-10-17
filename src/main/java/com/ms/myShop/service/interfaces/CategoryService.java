package com.ms.myShop.service.interfaces;

import com.ms.myShop.dto.request.CategoryRequest;
import com.ms.myShop.dto.response.CategoryResponse;
import com.ms.myShop.dto.response.Response;

import java.util.List;

public interface CategoryService {
    Response save(CategoryRequest categoryRequest);

    Response<List<CategoryResponse>> list();


    Response update(CategoryRequest categoryRequest);

    Response delete(Long categoryId);
}
