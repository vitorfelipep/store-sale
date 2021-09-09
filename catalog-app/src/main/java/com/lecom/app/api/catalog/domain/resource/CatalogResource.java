package com.lecom.app.api.catalog.domain.resource;

import com.lecom.app.api.catalog.domain.dto.CatalogPatchDTO;
import com.lecom.app.api.catalog.domain.dto.CatalogRequestDTO;
import com.lecom.app.api.catalog.domain.dto.CatalogViewtDTO;
import com.lecom.app.api.catalog.domain.service.CatalogService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/catalogs")
@Api(value = "Catalog")
public class CatalogResource {

    private final CatalogService catalogService;

    public CatalogResource(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public Page<CatalogViewtDTO> findAll(Pageable pageAble) {
        return catalogService.findAll(pageAble);
    }

    @GetMapping("/{catalogId}")
    public ResponseEntity<CatalogViewtDTO> findById(
        @PathVariable Long catalogId
    ) {
        var catalogViewtDTO = catalogService.findById(catalogId);
        return ResponseEntity.ok(catalogViewtDTO);
    }

    @PostMapping
    public ResponseEntity<CatalogRequestDTO> create(
        @Valid @RequestBody CatalogRequestDTO catalog
    ) {
        final var dto = catalogService.create(catalog);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @PatchMapping("/{catalogId}")
    public ResponseEntity<CatalogPatchDTO> patch(
        @PathVariable Long catalogId,
        @Valid @RequestBody CatalogPatchDTO catalog
    ) {
        final var dto = catalogService.patch(catalogId, catalog);
        return ResponseEntity.ok(dto);
    }

}
