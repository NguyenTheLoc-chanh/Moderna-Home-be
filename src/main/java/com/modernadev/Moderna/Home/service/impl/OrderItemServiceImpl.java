package com.modernadev.Moderna.Home.service.impl;

import com.modernadev.Moderna.Home.dto.OrderRequest;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.entity.Order;
import com.modernadev.Moderna.Home.entity.OrderItem;
import com.modernadev.Moderna.Home.entity.Product;
import com.modernadev.Moderna.Home.entity.User;
import com.modernadev.Moderna.Home.enums.OrderStatus;
import com.modernadev.Moderna.Home.exception.NotFoundException;
import com.modernadev.Moderna.Home.mapper.EntityDtoMapper;
import com.modernadev.Moderna.Home.repository.OrderItemRepo;
import com.modernadev.Moderna.Home.repository.OrderRepo;
import com.modernadev.Moderna.Home.repository.ProductRepo;
import com.modernadev.Moderna.Home.service.interf.OrderItemService;
import com.modernadev.Moderna.Home.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderRepo  orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        User user = userService.getLoginUser();

        // Map Order request items to order entities
        List<OrderItem> orderItems = orderRequest.getOrderItemRequestList().stream().map(orderItemRequest -> {
            Product product = productRepo.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
            orderItem.setOrderStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;
        }).collect(Collectors.toList());
        // Calculate the total price
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // Create order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);
        // Set the order reference in each order item
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        return Response.builder()
                .status(200)
                .message("Order was successfully placed.")
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        return null;
    }

    @Override
    public Response filterOrderItemStatus(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        return null;
    }
}
