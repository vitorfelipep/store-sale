package com.lecom.storeapp.domain.repository;

import com.lecom.storeapp.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("FROM Order o where o.customerId = :customerId")
    Page<Order> findAllByFiltering(Long customerId, Pageable pageable);

    @Query("FROM Order o where o.customerId = :customer and o.orderDate between :orderDateAt and :orderDateUntil")
    Page<Order> findOrdersByEmployeeAndWithDateRange(
            @Param("customer") Long customer,
            @Param("orderDateAt") LocalDateTime orderDateAt,
            @Param("orderDateUntil") LocalDateTime orderDateUntil,
            Pageable pageable
    );

    Optional<Order> findByIdAndTrackingCode(Long orderId, String trackingCode);
}
