package com.ms.myShop.controller;

import com.ms.myShop.dto.request.CategoryRequest;
import com.ms.myShop.dto.response.CategoryResponse;
import com.ms.myShop.dto.response.Response;
import com.ms.myShop.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public Response save ( @Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest);
    }


    @GetMapping("/list")
    public Response<List<CategoryResponse>> list(){
        return categoryService.list();

    }

    @PutMapping("/update")
    public Response update (@Valid @RequestBody  CategoryRequest categoryRequest){
        return categoryService.update(categoryRequest);
    }

    @PutMapping("/delete")
    public Response delete(Long categoryId){
        return categoryService.delete(categoryId);
    }
}
