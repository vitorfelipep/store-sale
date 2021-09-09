package com.lecom.app.api.catalog.domain.repository;

import com.lecom.app.api.catalog.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsByName(String name);

    @Query("FROM Product p where p.catalog.cnpj = :search")
    Page<Product> findAllByFiltering(
            @Param("search") String search,
            Pageable pageable
    );

}
