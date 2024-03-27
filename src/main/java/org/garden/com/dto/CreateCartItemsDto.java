package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateCartItemsDto {

    @Schema(description = "The ID of the product which is put into current cart")
    private long product_id;

    @Schema(description = "A count of products")
    private long quantity;

    public CreateCartItemsDto(long product_id, long quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public CreateCartItemsDto() {
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
