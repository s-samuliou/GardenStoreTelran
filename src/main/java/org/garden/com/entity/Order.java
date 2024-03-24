package org.garden.com.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Created at timestamp is required")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotBlank(message = "Delivery address is required")
    @Length(max = 500, message = "Address must be at least 10 characters and not nigher than 500")
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @NotNull(message = "Contact phone is required")
    @Column(name = "contact_phone")
    private String contactPhone;

    @NotBlank(message = "Delivery method is required")
    @Length(max = 255, message = "Delivery method must be less than 255 characters")
    @Column(name = "delivery_method")
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @NotNull(message = "Updated at timestamp is required")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Order() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Order(Long id, LocalDateTime createdAt, String deliveryAddress, String contactPhone, DeliveryType deliveryMethod, OrderStatus status, LocalDateTime updatedAt, User user) {
        this.id = id;
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.contactPhone = contactPhone;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        if (user != null) {
            return user.getId();
        }
        return null;
    }


    public void setUserId(long userId) {
        this.user.setId(userId);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}