package com.ms.myShop.controller.back;

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

    @PostMapping("/save/{name}")
    public Response save ( @Valid @PathVariable("name") String name){
        return categoryService.save(name);
    }


    @GetMapping("/list")
    public Response<List<CategoryResponse>> list(){
        return categoryService.list();

    }

    @PutMapping("/update/{categoryId}/{name}")
    public Response update(@PathVariable("categoryId") Long categoryId, @PathVariable("name") String name) {
        return categoryService.update(categoryId, name);
    }


    @PutMapping("/delete/{categoryId}")
    public Response delete(@PathVariable("categoryId") Long categoryId){
        return categoryService.delete(categoryId);
    }
}
