package com.lecom.storeapp.domain.repository;

import com.lecom.storeapp.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByCnpj(String cnpj);

}
