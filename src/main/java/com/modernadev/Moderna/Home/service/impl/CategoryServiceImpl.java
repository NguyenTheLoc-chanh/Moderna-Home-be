package com.modernadev.Moderna.Home.service.impl;

import com.modernadev.Moderna.Home.dto.CategoryDto;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.entity.Category;
import com.modernadev.Moderna.Home.exception.NotFoundException;
import com.modernadev.Moderna.Home.mapper.EntityDtoMapper;
import com.modernadev.Moderna.Home.repository.CategoryRepo;
import com.modernadev.Moderna.Home.service.interf.CategoryService;
import com.modernadev.Moderna.Home.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper  entityDtoMapper;
    private final UserService userService;

    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);

        return Response.builder()
                .status(200)
                .message("Category created successfully!")
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found!"));
        category.setName(categoryRequest.getName());

        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("Category updated successfully!")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(entityDtoMapper::mapCategoryToDtoBasic)
                .toList();
        if (categoryDtoList.isEmpty()) {
            throw new NotFoundException("Category not found!");
        }

        return Response.builder()
                .status(200)
                .message("Successfully!")
                .categoryDtoList(categoryDtoList)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found!"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);

        return Response.builder()
                .status(200)
                .message("Successfully!")
                .categoryDto(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found!"));
        categoryRepo.delete(category);

        return Response.builder()
                .status(200)
                .message("Category deleted successfully!")
                .build();
    }
}
