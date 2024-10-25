package com.ms.myShop.repository;

import com.ms.myShop.entity.Category;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByActive(Integer active);

    @Cacheable( key = "#categoryId", value = "categoryByIdAndActive")
    Category findByCategoryIdAndActive(Long categoryId, Integer active);
}
