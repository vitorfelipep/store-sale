package com.lecom.shippingcompany.api.domain.mappers;

import com.lecom.shippingcompany.api.domain.dto.ProductOrderDTO;
import com.lecom.shippingcompany.api.domain.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product productOrderDtoToProduct(ProductOrderDTO productOrderDTO);
    List<Product> productsOrdersDtosToProduts(List<ProductOrderDTO> productOrderDTOList);

}
