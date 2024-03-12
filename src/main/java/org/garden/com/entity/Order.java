package org.garden.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.garden.com.enums.OrderStatus;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "user_id")
    private long userId;

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
    private String deliveryMethod;

    private OrderStatus status;

    @NotNull(message = "Updated at timestamp is required")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

