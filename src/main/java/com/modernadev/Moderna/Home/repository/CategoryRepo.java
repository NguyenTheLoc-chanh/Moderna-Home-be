package com.modernadev.Moderna.Home.repository;

import com.modernadev.Moderna.Home.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
