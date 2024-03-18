package org.garden.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Column(name = "image_url")
    private String imageUrl;

    @DecimalMin(value = "0.0", message = "Price must be greatest then 0")
    @Column(name = "discount_price")
    private Double discountPrice;

    @NotNull(message = "Created at timestamp is required")
    @Column(name = "careated_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull(message = "Updated at timestamp is required")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "category_Id", referencedColumnName = "id")
    private Category category;

    public Product() {
    }

    public Product(Long id, String name, String description, double price, String imageUrl, Double discountPrice, LocalDateTime createdAt, LocalDateTime updatedAt, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.discountPrice = discountPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

        public long getCategoryId() {
        return category.getId();
    }

    public void setCategoryId(long categoryId) {
        this.category.setId(categoryId);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
