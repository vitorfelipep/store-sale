package com.lecom.app.api.catalog.domain.repository;

import com.lecom.app.api.catalog.domain.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByCnpj(String cnpj);

}
