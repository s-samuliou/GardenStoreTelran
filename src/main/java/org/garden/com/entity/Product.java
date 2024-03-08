package org.garden.com.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Length(max = 255, message = "Name must be less than 255 characters")
    @Column(name = "name")
    private String name;

    @Length(max = 510, message = "Description must be less than 510 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

    @NotNull(message = "Category Id is required")
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "image_url")
    private String imageUrl;

    @DecimalMin(value = "0.01", message = "Price must be greatest then 0")
    @Column(name = "discaunt_price")
    private double discountPrice;

    @NotNull(message = "Created at timestamp is required")
    @Column(name = "careated_at")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp is required")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
