package com.ms.myShop.service.impl;

import com.ms.myShop.dto.request.CategoryRequest;
import com.ms.myShop.dto.response.CategoryResponse;
import com.ms.myShop.dto.response.Response;
import com.ms.myShop.entity.Category;
import com.ms.myShop.enums.EnumAvailableStatus;
import com.ms.myShop.enums.ResponseCodes;
import com.ms.myShop.enums.ResponseMessages;
import com.ms.myShop.exception.ShopException;
import com.ms.myShop.repository.CategoryRepository;
import com.ms.myShop.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    @CachePut(key = "#root.methodName", value = "category")
    public Response save(CategoryRequest categoryRequest) {
        Response response = new Response<>();
        try {
            Category category = Category.builder()
                    .name(categoryRequest.getName())
                    .build();
            Category saved = categoryRepository.save(category);
            if (saved.getCategoryId() != null) {
                response.setMessage(ResponseMessages.SUCCESS.value);
                response.setStatusCode(ResponseCodes.SUCCESS.value);
            }
        } catch (ShopException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());

        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }

    @Override
    @Cacheable(key = "#root.methodName", value = "categories")
    public Response<List<CategoryResponse>> list() {

        Response<List<CategoryResponse>> response = new Response<>();
        try {

            List<Category> categoryList = categoryRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (categoryList == null || categoryList.isEmpty()) {
                throw new ShopException(ResponseMessages.CATEGORY_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }

            List<CategoryResponse> categoryResponseList = categoryList.stream().map(this::convert).toList();

            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);
            response.setBody(categoryResponseList);

        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }

    @Override
    @CachePut(value = "categories", key = "root.methodName + '_' + #categoryRequest.categoryId")
    @CacheEvict(value = "categories", allEntries = true)

    public Response update(CategoryRequest categoryRequest) {
        Response response = new Response<>();
        try {
            Category category = categoryRepository.findByCategoryIdAndActive(categoryRequest.getCategoryId(), EnumAvailableStatus.ACTIVE.value);
            if (category == null) {
                throw new ShopException(ResponseMessages.CATEGORY_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            category.setName(categoryRequest.getName());

            categoryRepository.save(category);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);

        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }

        return response;
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public Response delete(Long categoryId) {
        Response response = new Response<>();
        try {
            Category category = categoryRepository.findByCategoryIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.value);
            if (category == null) {
                throw new ShopException(ResponseMessages.CATEGORY_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            category.setActive(EnumAvailableStatus.DELETED.value);
            categoryRepository.save(category);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);

        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }

    private CategoryResponse convert(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }

}
