package com.lecom.shippingcompany.api.domain.mappers;

import com.lecom.shippingcompany.api.domain.dto.RequestTrackingDTO;
import com.lecom.shippingcompany.api.domain.entity.Tracking;
import org.mapstruct.Mapper;

@Mapper(uses = { ProductMapper.class, TargetAddressMappers.class })
public interface TrackingMapper {

    Tracking requestTrackingDtoToTracking(RequestTrackingDTO requestTrackingDTO);

}
