package com.lecom.shippingcompany.api.domain.repository;

import com.lecom.shippingcompany.api.domain.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    Optional<Tracking> findByOrderId(Long orderId);
    Optional<Tracking> findByOrderIdAndTrackindCode(Long orderId, String trackingCode);
}
