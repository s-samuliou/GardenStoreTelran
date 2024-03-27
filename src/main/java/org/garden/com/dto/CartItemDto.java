package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CartItemDto {

    @Schema(description = "Unique identifier of the cartItems")
    private long id;

    @Schema(description = "The ID of the cart to which the item belongs")
    private long cart_id;

    @Schema(description = "The ID of the product which is put into current cart")
    private long product_id;
    @Schema(description = "A count of products")
    private long quantity;

    public CartItemDto(long id, long cart_id, long product_id, long quantity) {
        this.id = id;
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCart_id() {
        return cart_id;
    }

    public void setCart_id(long cart_id) {
        this.cart_id = cart_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
