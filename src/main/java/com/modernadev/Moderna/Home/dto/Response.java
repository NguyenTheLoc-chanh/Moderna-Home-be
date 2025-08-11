package com.modernadev.Moderna.Home.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int status;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private String token;
    private String role;
    private String expirationTime;

    private int totalPage;
    private long totalElements;

    private AddressDto addressDto;

    private UserDto userDto;
    private List<UserDto> userDtoList;

    private CategoryDto categoryDto;
    private List<CategoryDto> categoryDtoList;

    private ProductDto productDto;
    private List<ProductDto> productDtoList;

    private OrderItemDto orderItemDto;
    private List<OrderItemDto> orderItemDtoList;

    private OrderDto orderDto;
    private List<OrderDto> orderDtoList;

}
