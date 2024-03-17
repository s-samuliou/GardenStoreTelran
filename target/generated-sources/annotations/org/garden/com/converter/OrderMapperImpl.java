package org.garden.com.converter;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-14T20:43:42+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto orderToOrderDto(Order order) {
        if ( order == null ) {
            return null;
        }

        Long id = null;
        long userId = 0L;
        LocalDateTime createdAt = null;
        String deliveryAddress = null;
        String contactPhone = null;
        DeliveryType deliveryMethod = null;
        OrderStatus status = null;
        LocalDateTime updatedAt = null;

        id = order.getId();
        userId = order.getUserId();
        createdAt = order.getCreatedAt();
        deliveryAddress = order.getDeliveryAddress();
        contactPhone = order.getContactPhone();
        if ( order.getDeliveryMethod() != null ) {
            deliveryMethod = Enum.valueOf( DeliveryType.class, order.getDeliveryMethod() );
        }
        status = order.getStatus();
        updatedAt = order.getUpdatedAt();

        OrderDto orderDto = new OrderDto( id, userId, createdAt, deliveryAddress, contactPhone, deliveryMethod, status, updatedAt );

        return orderDto;
    }

    @Override
    public OrderCreateDto orderToOrderCreateDto(Order order) {
        if ( order == null ) {
            return null;
        }

        LocalDateTime createdAt = null;
        String deliveryAddress = null;
        String contactPhone = null;
        DeliveryType deliveryMethod = null;
        OrderStatus status = null;
        LocalDateTime updatedAt = null;

        createdAt = order.getCreatedAt();
        deliveryAddress = order.getDeliveryAddress();
        contactPhone = order.getContactPhone();
        if ( order.getDeliveryMethod() != null ) {
            deliveryMethod = Enum.valueOf( DeliveryType.class, order.getDeliveryMethod() );
        }
        status = order.getStatus();
        updatedAt = order.getUpdatedAt();

        OrderCreateDto orderCreateDto = new OrderCreateDto( createdAt, deliveryAddress, contactPhone, deliveryMethod, status, updatedAt );

        return orderCreateDto;
    }

    @Override
    public Order orderDtoToOrder(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( orderDto.getId() );
        order.setUserId( orderDto.getUserId() );
        order.setCreatedAt( orderDto.getCreatedAt() );
        order.setDeliveryAddress( orderDto.getDeliveryAddress() );
        order.setContactPhone( orderDto.getContactPhone() );
        if ( orderDto.getDeliveryMethod() != null ) {
            order.setDeliveryMethod( orderDto.getDeliveryMethod().name() );
        }
        order.setStatus( orderDto.getStatus() );
        order.setUpdatedAt( orderDto.getUpdatedAt() );

        return order;
    }

    @Override
    public Order createOrderDtoToOrder(OrderCreateDto orderCreateDto) {
        if ( orderCreateDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setCreatedAt( orderCreateDto.getCreatedAt() );
        order.setDeliveryAddress( orderCreateDto.getDeliveryAddress() );
        order.setContactPhone( orderCreateDto.getContactPhone() );
        if ( orderCreateDto.getDeliveryMethod() != null ) {
            order.setDeliveryMethod( orderCreateDto.getDeliveryMethod().name() );
        }
        order.setStatus( orderCreateDto.getStatus() );
        order.setUpdatedAt( orderCreateDto.getUpdatedAt() );

        return order;
    }
}
