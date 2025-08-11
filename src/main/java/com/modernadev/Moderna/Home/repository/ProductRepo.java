package com.modernadev.Moderna.Home.repository;

import com.modernadev.Moderna.Home.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(long categoryId);

    List<Product> findByNameOrDescriptionContaining(String name, String description);
}
