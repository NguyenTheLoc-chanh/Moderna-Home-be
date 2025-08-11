package com.modernadev.Moderna.Home.specification;

import com.modernadev.Moderna.Home.entity.OrderItem;
import com.modernadev.Moderna.Home.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderItemSpecification {

    /** Generate specification to filter orderItems by status*/
    public static Specification<OrderItem> hasStatus(OrderStatus orderStatus) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                orderStatus !=null ? criteriaBuilder.equal(root.get("status"), orderStatus) : criteriaBuilder.conjunction());
    }
    /** Generate specification to filter order items by data range*/
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ((root, criteriaQuery, criteriaBuilder) ->{
            if(startDate != null && endDate != null){
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            }else if(startDate != null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            }else if(endDate != null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }else {
                return null;
            }
        });
    }

    /** Generate specification to filter orderItems by item id*/
    public static Specification<OrderItem> hasItemId(Long itemId) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId) : null);
    }
}
