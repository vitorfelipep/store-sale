package com.lecom.storeapp.domain.repository;

import com.lecom.storeapp.domain.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

}
