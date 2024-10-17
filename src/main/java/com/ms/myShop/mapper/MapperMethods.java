package com.ms.myShop.mapper;


import com.ms.myShop.dto.request.ProductRequest;
import com.ms.myShop.dto.response.CategoryResponse;
import com.ms.myShop.dto.response.ProductResponse;
import com.ms.myShop.entity.Category;
import com.ms.myShop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;


@Mapper(componentModel = "spring")
public interface MapperMethods {


//   CategoryResponse fromCategoryToCategoryResponse(Category category);
//   List<CategoryResponse> fromEntityListToResponseList(List<Category> categoryList);
////   @Mapping(source = "category.name", target = "category")
//   ProductResponse NamefromProductToProductResponse(Product product);
//   List<ProductResponse> fromProductListToProductResponseList(List<Product> productList);
//
//   Product fromProductResquestToProduct(ProductRequest request);
}
