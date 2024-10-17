package com.ms.myShop.repository;

import com.ms.myShop.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"image", "category"})
    List<Product> findAllByActive(Integer active);
    @EntityGraph(attributePaths = {"image", "category"})
    Product findByProductIdAndActive(Long productId, Integer active);
}
