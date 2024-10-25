package com.ms.myShop.repository;

import com.ms.myShop.entity.Category;
import com.ms.myShop.entity.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"image", "category"})
    Page<Product> findAllByActive(Integer active, Pageable pageable);
    @EntityGraph(attributePaths = {"image", "category"})
    @Cacheable(key = "#productId",value = "product" )
    Product findByProductIdAndActive(Long productId, Integer active);
    @Query("SELECT COUNT(p) FROM Product p WHERE p.active = :active AND p.category = :category")
    long countByActiveAndCategory(@Param("active") Integer active, @Param("category") Category category);

}