package com.lecom.storeapp.domain.mappers;

import com.lecom.storeapp.domain.dto.OrderRequestDTO;
import com.lecom.storeapp.domain.dto.OrderViewDTO;
import com.lecom.storeapp.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(uses = OrderItemMapper.class)
public interface OrderMapper {

    Order orderRequestDtoToOrder(OrderRequestDTO orderRequestDTO);
    OrderViewDTO orderToOrderViewDTO(Order order);

}
