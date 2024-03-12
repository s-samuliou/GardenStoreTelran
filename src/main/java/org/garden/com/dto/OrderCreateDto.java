package org.garden.com.dto;

import lombok.Getter;
import lombok.Setter;
import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderCreateDto {

    private Long id;

    private long userId;

    private LocalDateTime createdAt;

    private String deliveryAddress;

    private String contactPhone;

    private DeliveryType deliveryMethod;

    private OrderStatus status;

    private LocalDateTime updatedAt;

    public OrderCreateDto(Long id, long userId, LocalDateTime createdAt,
                          String deliveryAddress, String contactPhone, DeliveryType deliveryMethod,
                          OrderStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.contactPhone = contactPhone;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
