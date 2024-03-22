package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderCreateDto {

    @Schema(description = "The local timestamp of creating order", example = "2024-03-21T12:55:00")
    private LocalDateTime createdAt;

    @Schema(description = "The delivery address of the order", example = "Germany")
    private String deliveryAddress;

    @Schema(description = "Contact phone of the user", example = "94321120")
    private String contactPhone;

    @Schema(description = "Delivery type of the order", example = "COURIER")
    private DeliveryType deliveryMethod;

    @Schema(description = "Order status", example = "PAID")
    private OrderStatus status;

    @Schema(description = "The local timestamp of updating order", example = "2024-03-22T15:55:00")
    private LocalDateTime updatedAt;

    @Schema(description = "The user ID to which the order is belongs to", example = "1")
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