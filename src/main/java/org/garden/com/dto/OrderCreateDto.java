package org.garden.com.dto;

import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderCreateDto {


    private LocalDateTime createdAt;

    private String deliveryAddress;

    private String contactPhone;

    private DeliveryType deliveryMethod;

    private OrderStatus status;

    private LocalDateTime updatedAt;

    private long userId;

    public OrderCreateDto(LocalDateTime createdAt, String deliveryAddress, String contactPhone, DeliveryType deliveryMethod, OrderStatus status, LocalDateTime updatedAt, long userId) {
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.contactPhone = contactPhone;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.updatedAt = updatedAt;
        this.userId = userId;
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
