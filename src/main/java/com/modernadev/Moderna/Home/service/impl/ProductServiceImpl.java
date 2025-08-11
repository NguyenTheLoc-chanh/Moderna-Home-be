package com.modernadev.Moderna.Home.service.impl;

import com.modernadev.Moderna.Home.dto.ProductDto;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.entity.Category;
import com.modernadev.Moderna.Home.entity.Product;
import com.modernadev.Moderna.Home.exception.NotFoundException;
import com.modernadev.Moderna.Home.mapper.EntityDtoMapper;
import com.modernadev.Moderna.Home.repository.CategoryRepo;
import com.modernadev.Moderna.Home.repository.ProductRepo;
import com.modernadev.Moderna.Home.service.AwsS3Service;
import com.modernadev.Moderna.Home.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper  entityDtoMapper;
    private final AwsS3Service awsS3Service;

    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        String productImage = awsS3Service.saveImageToS3(image);

        Product product = new Product();
        product.setCategory(category);
        product.setImageUrl(productImage);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product Created Successfully!")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        Category category = null;
        String productImage = null;
        if(categoryId != null) {
            category =  categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        }
        if(image != null && !image.isEmpty()) {
            productImage = awsS3Service.saveImageToS3(image);
        }

        if(category != null) product.setCategory(category);
        if(name != null) product.setName(name);
        if(description != null) product.setDescription(description);
        if(price != null) product.setPrice(price);
        if(productImage != null) product.setImageUrl(productImage);
        productRepo.save(product);

        return Response.builder()
                .status(200)
                .message("Product Updated Successfully!")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        if (product != null) {
            productRepo.delete(product);
        }
        return Response.builder()
                .status(200)
                .message("Product Deleted Successfully!")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .message("Product Details Updated Successfully!")
                .productDto(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<Product> products = productRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());
        if(productDtoList.isEmpty()) {
            throw new NotFoundException("Product Not Found");
        }
        return Response.builder()
                .status(200)
                .message("Successfully!")
                .productDtoList(productDtoList)
                .build();
    }

    @Override
    public Response getProductByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        if(products.isEmpty()) {
            throw new NotFoundException("Product Not Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .message("Successfully!")
                .productDtoList(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameOrDescriptionContaining(searchValue, searchValue);
        if(products.isEmpty()) {
            throw new NotFoundException("Products Not Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .message("Successfully!")
                .productDtoList(productDtoList)
                .build();
    }
}
