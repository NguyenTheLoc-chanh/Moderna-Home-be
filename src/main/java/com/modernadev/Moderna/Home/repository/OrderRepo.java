package com.modernadev.Moderna.Home.repository;

import com.modernadev.Moderna.Home.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
