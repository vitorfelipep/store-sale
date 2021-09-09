package com.lecom.storeapp.domain.service;

import com.google.common.base.Preconditions;
import com.lecom.storeapp.domain.dto.*;
import com.lecom.storeapp.domain.entity.Store;
import com.lecom.storeapp.domain.mappers.StoreMapper;
import com.lecom.storeapp.domain.repository.StoreRepository;
import com.lecom.storeapp.domain.service.exception.StoreAlreadyExistsException;
import com.lecom.storeapp.domain.service.exception.StoreNotFoundException;
import com.lecom.storeapp.util.RestPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private Logger logger = LoggerFactory.getLogger(StoreService.class);

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final String uriCatalogs;

    public StoreService(
            StoreRepository storeRepository,
            StoreMapper storeMapper,
            @Value("${apps.catalop.products.uri}")
            String uriCatalogs
    ) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.uriCatalogs = uriCatalogs;
    }

    public Page<StoreViewtDTO> findAll(final Pageable pageAble) {
        final var catalogPage = storeRepository.findAll(pageAble);
        if (catalogPage.hasContent()) {
            final var catalogDTOList = catalogPage.getContent()
                    .stream().map(StoreViewtDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(
                    catalogDTOList,
                    catalogPage.getPageable(),
                    catalogPage.getTotalElements()
            );
        }
        return Page.empty();
    }

    public StoreViewtDTO findById(final Long id) {
        var optionalCatalog = storeRepository.findById(id);
        if (optionalCatalog.isEmpty()) {
            throw new StoreNotFoundException(String.format("Loja não encontrada com o id: %s", id));
        }
        return new StoreViewtDTO(optionalCatalog.get());
    }

    public URI create(StoreRequestDTO store) {
        AtomicReference<Store> atomicStore = new AtomicReference();
        logger.info("STORE-STORE-CREATE: receiving a new store {}", store);
        storeRepository.findByCnpj(store.getCnpj()).ifPresentOrElse(storeFound -> {
            logger.error("STORE-CREATE-ERROR: Store with cnpj {} already exists", storeFound.getCnpj());
            throw new StoreAlreadyExistsException(String.format("Store with cnpj %s already exists.", storeFound.getCnpj()));
        }, () -> {
            logger.info("STORE-CREATE: Creating a new store {}", store.getName());
            final var newsStore = storeMapper.storeRequestDtoToStore(store);
            atomicStore.set(storeRepository.save(newsStore));
        });

        return ServletUriComponentsBuilder
                .fromPath("/v1/shops")
                .path("/{id}")
                .build(atomicStore.get().getStoreId());
    }

    public StorePatchDTO patch(Long id, StorePatchDTO store) {
        Preconditions.checkArgument(id != null, "Id não pode ser nulo", id);

        Optional<Store> storeOptional = storeRepository.findById(id);
        if (storeOptional.isEmpty()) {
            throw new StoreNotFoundException(String.format("Loja não encontrada com o id: %s", id));
        }

        Store storeToUpdate = storeOptional.get();
        storeToUpdate.setName(store.getName());
        storeToUpdate.setStatus(store.getStatus());
        storeToUpdate.setCep(store.getCep());
        storeToUpdate.setStreet(store.getStreet());
        storeToUpdate.setAddressNumber(store.getNumber());
        storeToUpdate.setCity(store.getCity());
        storeToUpdate.setState(store.getState());
        storeToUpdate.setCountry(store.getCountry());
        storeToUpdate.setReferencePoint(store.getReferencePoint());
        storeToUpdate.setChangeUser(store.getChangeUser());

        storeRepository.save(storeToUpdate);

        return store;
    }

    public Page<ProductViewDTO> findItemsStore(Long id, Pageable pageable) {
        StoreViewtDTO storeViewtDTO = findById(id);
        RestTemplate restTemplate = new RestTemplate();
        String urlRequest = uriCatalogs.concat(
                String.format("?direction=%s&page=%s&search=%s&size=%s&sort=%s",
                        Sort.Direction.ASC,
                        pageable.getPageNumber(),
                        storeViewtDTO.getCnpj(),
                        pageable.getPageSize(),
                        "id"));
        ParameterizedTypeReference<RestPageImpl<ProductViewDTO>> responseType = new ParameterizedTypeReference<>() { };
        ResponseEntity<RestPageImpl<ProductViewDTO>> result = restTemplate
                .exchange(urlRequest, HttpMethod.GET, null, responseType);
        if (result.getStatusCode().is2xxSuccessful()) {
           return result.getBody().map(it -> it);
        }
        return Page.empty();
    }
}
