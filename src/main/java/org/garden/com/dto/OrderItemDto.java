package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderItemDto {

    @Schema(description = "The unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "The order ID to which the order is belongs to", example = "1")
    private Long orderId;

    @Schema(description = "The product ID to which the product is belongs to", example = "1")
    private Long productId;

    @Schema(description = "The quantity of the chosen products", example = "5")
    private int quantity;

    @Schema(description = "The price of the chosen products which has to be paid", example = "50")
    private double priceAtPurchase;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, Long orderId, Long productId, int quantity, double priceAtPurchase) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
