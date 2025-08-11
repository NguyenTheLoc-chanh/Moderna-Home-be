package com.modernadev.Moderna.Home.service.interf;

import com.modernadev.Moderna.Home.dto.OrderRequest;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItemStatus(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}
