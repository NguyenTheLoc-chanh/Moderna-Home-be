package com.modernadev.Moderna.Home.controller;

import com.modernadev.Moderna.Home.dto.OrderRequest;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.entity.OrderItem;
import com.modernadev.Moderna.Home.enums.OrderStatus;
import com.modernadev.Moderna.Home.service.interf.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public ResponseEntity<Response> placeOrder(@RequestBody OrderRequest  orderRequest){
        return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
    }

    @PutMapping("/update-item-status/{orderItemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable Long orderItemId, @RequestParam String status){
        return ResponseEntity.ok(orderItemService.updateOrderItemStatus(orderItemId, status));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> filterOrderItemStatus(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
            @RequestParam(required = false)  Long itemId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int pageSize
            )
    {
        Pageable  pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,"id"));
        OrderStatus orderStatus = status == null ? null : OrderStatus.valueOf(status.toUpperCase());

        return ResponseEntity.ok(orderItemService.filterOrderItemStatus(orderStatus, startDate, endDate, itemId, pageable));
    }
}
