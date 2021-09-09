package com.lecom.app.api.catalog.domain.service;

import com.google.common.base.Preconditions;
import com.lecom.app.api.catalog.domain.dto.CatalogPatchDTO;
import com.lecom.app.api.catalog.domain.dto.CatalogRequestDTO;
import com.lecom.app.api.catalog.domain.dto.CatalogViewtDTO;
import com.lecom.app.api.catalog.domain.entity.Catalog;
import com.lecom.app.api.catalog.domain.repository.CatalogRepository;
import com.lecom.app.api.catalog.domain.service.exception.CatalogAlreadyExistsException;
import com.lecom.app.api.catalog.domain.service.exception.CatalogNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Page<CatalogViewtDTO> findAll(Pageable pageAble) {
        final var catalogPage = catalogRepository.findAll(pageAble);
        if (catalogPage.hasContent()) {
            final var catalogDTOList = catalogPage.getContent()
                    .stream().map(CatalogViewtDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(
                    catalogDTOList,
                    catalogPage.getPageable(),
                    catalogPage.getTotalElements()
            );
        }
        return Page.empty();
    }

    public CatalogRequestDTO create(CatalogRequestDTO catalog) {
        catalogRepository.findByCnpj(catalog.getCnpj())
                .ifPresent(catalog1 -> { throw new CatalogAlreadyExistsException("Já existe uma catalogo para esta empresa!"); });
        final var catalogToSave = new Catalog(catalog);
        return new CatalogRequestDTO(catalogRepository.saveAndFlush(catalogToSave));
    }

    public CatalogPatchDTO patch(
        Long catalogId,
        CatalogPatchDTO catalog
    ) {
        Preconditions.checkArgument(catalogId != null, "Id não pode ser nulo", catalogId);
        return catalog;
    }

    public CatalogViewtDTO findById(Long catalogId) {
        var optionalCatalog = catalogRepository.findById(catalogId);
        if (optionalCatalog.isEmpty()) {
            throw new CatalogNotFoundException(String.format("Catalogo não encontrado com o id: %s", catalogId));
        }
        return new CatalogViewtDTO(optionalCatalog.get());
    }
}
