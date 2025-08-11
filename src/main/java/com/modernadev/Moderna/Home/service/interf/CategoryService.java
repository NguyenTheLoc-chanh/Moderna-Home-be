package com.modernadev.Moderna.Home.service.interf;

import com.modernadev.Moderna.Home.dto.CategoryDto;
import com.modernadev.Moderna.Home.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId ,CategoryDto categoryRequest);

    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}
