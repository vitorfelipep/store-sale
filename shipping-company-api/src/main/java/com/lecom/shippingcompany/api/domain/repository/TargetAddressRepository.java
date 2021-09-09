package com.lecom.shippingcompany.api.domain.repository;

import com.lecom.shippingcompany.api.domain.entity.TargetAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetAddressRepository extends JpaRepository<TargetAddress, Long> {
}
