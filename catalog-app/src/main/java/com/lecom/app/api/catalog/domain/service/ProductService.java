package com.lecom.app.api.catalog.domain.service;

import com.google.common.base.Preconditions;
import com.lecom.app.api.catalog.domain.dto.ProductRequestDTO;
import com.lecom.app.api.catalog.domain.dto.ProductViewDTO;
import com.lecom.app.api.catalog.domain.entity.Product;
import com.lecom.app.api.catalog.domain.repository.CatalogRepository;
import com.lecom.app.api.catalog.domain.repository.ProductRepository;
import com.lecom.app.api.catalog.domain.service.exception.ProductAlreadyExistsException;
import com.lecom.app.api.catalog.domain.service.exception.ProductNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;

    public ProductService(ProductRepository productRepository, CatalogRepository catalogRepository) {
        this.productRepository = productRepository;
        this.catalogRepository = catalogRepository;
    }

    public URI create(Long storeId, ProductRequestDTO productRequestDTO) {
        AtomicReference<Product> product = new AtomicReference();
        logger.info("STORE-PRODUCT-CREATE: receiving product {} to store id {}", productRequestDTO, storeId);
        catalogRepository.findById(storeId).ifPresentOrElse(catalog -> {
            logger.info("STORE-PRODUCT-CREATE: Catalog {} found with id {}", catalog.getName(), storeId);
            product.set(Product.builder()
                    .catalog(catalog)
                    .creationUser(productRequestDTO.getCreationUser())
                    .name(productRequestDTO.getName())
                    .status(productRequestDTO.getStatus())
                    .unitPrice(productRequestDTO.getUnitPrice())
                    .category(productRequestDTO.getCategory())
                    .build());

            if (checkIfExistsByName(productRequestDTO.getName())) {
                logger.error("STORE-PRODUCT-CREATE-ERROR: Already exist a product with this name: {}", productRequestDTO.getName());
                throw new ProductAlreadyExistsException(String.format("Já existe um produto com este nome: %s",
                        productRequestDTO.getName()));
            }

            product.set(productRepository.saveAndFlush(product.get()));

        }, () -> {
            logger.error("STORE-PRODUCT-CREATE-ERROR: Catalog not found with storeId: {}", storeId);
            throw new EmptyResultDataAccessException(String.format("No store with id %s were found.", storeId), 1);
        } );

        return ServletUriComponentsBuilder
                .fromPath("/v1/products")
                .path("/{id}")
                .build(product.get().getId());
    }

    private Boolean checkIfExistsByName(String name) {
        return productRepository.existsByName(name);
    }

    public Page<ProductViewDTO> findAll(Pageable pageAble) {
        final var productPage = productRepository.findAll(pageAble);
        if (productPage.hasContent()) {
            final var productList = productPage.getContent()
                    .stream().map(ProductViewDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(
                    productList,
                    productPage.getPageable(),
                    productPage.getTotalElements()
            );
        }
        return Page.empty();
    }

    public Page<ProductViewDTO> findAll(
            final String search,
            final Integer page,
            final Integer size,
            final String sort,
            final String direction
    ) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort).toOptional();
        if (StringUtils.isNotEmpty(search)) {
            Page<Product> productsPage = productRepository.findAllByFiltering(search, pageable.get());
            if (productsPage.hasContent()) {
                final var productList = productsPage.getContent()
                        .stream().map(ProductViewDTO::new)
                        .collect(Collectors.toList());
                return new PageImpl<>(
                        productList,
                        productsPage.getPageable(),
                        productsPage.getTotalElements()
                );
            }
        } else {
            return findAll(pageable.get());
        }

        return Page.empty();
    }

    public ProductViewDTO findById(Long id) {
        var optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Produto não encontrado com o id: %s", id));
        }
        return new ProductViewDTO(optionalProduct.get());
    }

}
