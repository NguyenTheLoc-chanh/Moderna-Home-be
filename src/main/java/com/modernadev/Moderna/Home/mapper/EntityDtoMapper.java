package com.modernadev.Moderna.Home.mapper;

import com.modernadev.Moderna.Home.dto.*;
import com.modernadev.Moderna.Home.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {
    //User Entity to UserDto
    public UserDto mapUserToDtoBasic(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setUserRole(user.getUserRole().name());

        return userDto;
    }

    // Address to DTO Basic
    public AddressDto mapAddressToDtoBasic(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setZipCode(address.getZipCode());
        return  addressDto;
    }

    // Category to DTO Basic
    public CategoryDto mapCategoryToDtoBasic(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    //OrderItem to Dto Basic
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getOrderStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    // Product to Dto Basic
    public ProductDto mapProductToDtoBasic(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    public UserDto mapUserToDtoPlusAddress(User user){
        UserDto userDto = mapUserToDtoBasic(user);
        if(user.getAddress() != null){
            AddressDto addressDtoPlusAddress = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddressDto(addressDtoPlusAddress);
        }
        return userDto;
    }

    // OrderItem to DTO Plus Product
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);
        if(orderItem.getProduct() != null){
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProductDto(productDto);
        }
        return orderItemDto;
    }

    // OrderItem to DTO Plus Product and User
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);
        if(orderItem.getUser() != null){
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUserDto(userDto);
        }
        return orderItemDto;
    }

    // User to DTO with Address and OrderItem History
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user){
        UserDto userDto = mapUserToDtoPlusAddress(user);
        if(user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()){
            userDto.setOrderItemDtoList(user.getOrderItemList()
            .stream().map(this::mapOrderItemToDtoPlusProduct)
            .collect(Collectors.toList()));
        }
        return userDto;
    }
}
