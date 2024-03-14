package org.garden.com.dto;

import lombok.Getter;
import lombok.Setter;
import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderDto {

    private Long id;

    private long userId;

    private LocalDateTime createdAt;

    private String deliveryAddress;

    private String contactPhone;

    private DeliveryType deliveryMethod;

    private OrderStatus status;

    private LocalDateTime updatedAt;

    public OrderDto(Long id, long userId, LocalDateTime createdAt, String deliveryAddress,
                    String contactPhone, DeliveryType deliveryMethod, OrderStatus status,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.contactPhone = contactPhone;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public DeliveryType getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryType deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
