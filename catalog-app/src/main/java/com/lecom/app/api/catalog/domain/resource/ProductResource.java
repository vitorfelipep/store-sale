package com.lecom.app.api.catalog.domain.resource;

import com.lecom.app.api.catalog.domain.dto.ProductRequestDTO;
import com.lecom.app.api.catalog.domain.dto.ProductViewDTO;
import com.lecom.app.api.catalog.domain.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@Api(value = "Product")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os produtos cadastrados no sistema com filtros **search (cnpj da loja) com ordenação e paginação", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Retorna uma lista de produtos com paginação", response = Page.class),
        @ApiResponse(code = 404, message = "product.not.found")
    })
    public Page<ProductViewDTO> findAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        return productService.findAll(search, page, size, sort, direction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> findById(
        @PathVariable Long id
    ) {
        var productViewDTO = productService.findById(id);
        return ResponseEntity.ok(productViewDTO);
    }

    @PostMapping("/store/{storeId}")
    public ResponseEntity create(
       @PathVariable Long storeId,
       @Valid @RequestBody ProductRequestDTO productRequestDTO
    ) {
        URI uri = productService.create(storeId, productRequestDTO);
        return ResponseEntity.created(uri).build();
    }

}
