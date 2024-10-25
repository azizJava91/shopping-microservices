package com.ms.myShop.service.interfaces;

import com.ms.myShop.dto.request.CategoryRequest;
import com.ms.myShop.dto.response.CategoryResponse;
import com.ms.myShop.dto.response.Response;

import java.util.List;

public interface CategoryService {
    Response save(String name);

    Response<List<CategoryResponse>> list();


    Response update(Long categoryId, String name);

    Response delete(Long categoryId);
}
