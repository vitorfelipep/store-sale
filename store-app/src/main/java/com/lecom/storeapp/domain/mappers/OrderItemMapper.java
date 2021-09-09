package com.lecom.storeapp.domain.mappers;

import com.lecom.storeapp.domain.dto.OrderItemRequestDTO;
import com.lecom.storeapp.domain.dto.OrderItemViewDTO;
import com.lecom.storeapp.domain.entity.Order;
import com.lecom.storeapp.domain.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface OrderItemMapper {

    OrderItem orderItemDtoToOrderItem(OrderItemRequestDTO orderItemDTO);
    Set<OrderItem> ordersItemsDtoToOrdersItems(Set<OrderItemRequestDTO> orderItemRequestDTOS);
    OrderItemViewDTO orderToOrderViewDTO(OrderItem order);
}
