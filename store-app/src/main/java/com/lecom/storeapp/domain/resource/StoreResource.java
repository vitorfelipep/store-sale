package com.lecom.storeapp.domain.resource;

import com.lecom.storeapp.domain.dto.*;
import com.lecom.storeapp.domain.service.StoreService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/shops")
@Api(value = "Store resource")
public class StoreResource {

    private final StoreService storeService;

    public StoreResource(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public Page<StoreViewtDTO> findAll(Pageable pageAble) {
        return storeService.findAll(pageAble);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreViewtDTO> findById(
            @PathVariable Long id
    ) {
        var storeViewtDTO = storeService.findById(id);
        return ResponseEntity.ok(storeViewtDTO);
    }

    @GetMapping("/{id}/catalog/items")
    public ResponseEntity<Page<ProductViewDTO>> findItemsStore(
            @PathVariable Long id,
            Pageable pageable
    ) {
        Page<ProductViewDTO> storeItemsViewtDTO = storeService.findItemsStore(id, pageable);
        return ResponseEntity.ok(storeItemsViewtDTO);
    }

    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody StoreRequestDTO store
    ) {
        final URI uri  = storeService.create(store);
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StorePatchDTO> patch(
            @PathVariable Long id,
            @Valid @RequestBody StorePatchDTO store
    ) {
        final var dto = storeService.patch(id, store);
        return ResponseEntity.ok(dto);
    }

}
