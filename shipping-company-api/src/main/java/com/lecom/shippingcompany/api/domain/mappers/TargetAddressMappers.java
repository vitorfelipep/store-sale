package com.lecom.shippingcompany.api.domain.mappers;

import com.lecom.shippingcompany.api.domain.dto.TargetAddressDTO;
import com.lecom.shippingcompany.api.domain.entity.TargetAddress;
import org.mapstruct.Mapper;

@Mapper
public interface TargetAddressMappers {

    TargetAddress targetAddressDtoToTargetAddress(TargetAddressDTO targetAddressDTO);

}
