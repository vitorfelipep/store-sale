package com.lecom.storeapp.domain.mappers;

import com.lecom.storeapp.domain.dto.StorePatchDTO;
import com.lecom.storeapp.domain.dto.StoreRequestDTO;
import com.lecom.storeapp.domain.entity.Store;
import org.mapstruct.Mapper;

@Mapper
public interface StoreMapper {

    Store storePatchDtoToStore(StorePatchDTO storePatchDTO);
    StorePatchDTO storeToStorePatchDto(Store store);

    Store storeRequestDtoToStore(StoreRequestDTO storeRequestDTO);
    StoreRequestDTO storeToStoreRequestDto(Store store);

}
