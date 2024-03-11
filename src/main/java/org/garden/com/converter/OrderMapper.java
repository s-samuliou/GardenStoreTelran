package org.garden.com.converter;

import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "id")
    OrderDto orderToOrderDto(Order order);

    OrderCreateDto orderToOrderCreateDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);

    Order createOrderDtoToOrder(OrderCreateDto orderCreateDto);
}
