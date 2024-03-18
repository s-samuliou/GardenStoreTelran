package org.garden.com.converter;

import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "deliveryAddress", target = "deliveryAddress")
    @Mapping(source = "contactPhone", target = "contactPhone")
    @Mapping(source = "deliveryMethod", target = "deliveryMethod")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "updatedAt", target = "updatedAt")
    OrderDto orderToOrderDto(Order order);

    OrderCreateDto orderToOrderCreateDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "deliveryAddress", target = "deliveryAddress")
    @Mapping(source = "contactPhone", target = "contactPhone")
    @Mapping(source = "deliveryMethod", target = "deliveryMethod")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Order createOrderDtoToOrder(OrderCreateDto orderCreateDto);
}
