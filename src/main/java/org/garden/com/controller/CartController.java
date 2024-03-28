package org.garden.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.CartItemsMapper;
import org.garden.com.dto.CartItemDto;
import org.garden.com.dto.CreateCartItemsDto;
import org.garden.com.entity.CartItem;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.CartItemNotFoundException;
import org.garden.com.exceptions.InvalidCartItemException;
import org.garden.com.service.CartItemsServiceImpl;
import org.garden.com.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart Controller", description = "Handles operations related to cart")
@RestController
@RequestMapping("/v1/cart")
public class CartController {
    @Autowired
    private CartItemsServiceImpl cartItemsService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CartItemsMapper cartMapper;

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Operation(
            summary = "Get all products from the cart",
            description = "Retrieves a list of all products from cart",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{cartId}")
    public List<CartItemDto> getAllItemsFromCart(@PathVariable Long cartId) {
        log.info("Received request to get all cart items");
        List<CartItem> itemList = cartItemsService.getListOfItems(cartId);
        List<CartItemDto> itemDtos = itemList.stream().map( item -> cartMapper.cartItemsToCartItemsDto(item)).toList();
        log.info("Found {} products", itemDtos.size());
        return itemDtos;
    }




    @Operation(
            summary = "Add product into the cart",
            description = "Puts existing product into the cart",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added product"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping("/{userId}")
    public ResponseEntity<CreateCartItemsDto> addProductIntoCart(@PathVariable long userId, @RequestBody CreateCartItemsDto createCartItemsDto){
        log.info("Received request to add product with id {} into the cart", createCartItemsDto.getProduct_id());
        Product product = productService.findProductById(createCartItemsDto.getProduct_id());
        CartItem added = productService.addProductToCart(product, createCartItemsDto.getQuantity(), userId);
        CreateCartItemsDto savedCartItemsDto = cartMapper.cartItemsToCreateCartItemsDto(added);
        log.info("Product {} added into cart ", savedCartItemsDto.getProduct_id());
        return ResponseEntity.status(HttpStatus.OK).body(savedCartItemsDto);
    }


    @Operation(
            summary = "Delete cart item by ID",
            description = "Deletes cart item with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted cart item"),
                    @ApiResponse(responseCode = "404", description = "Cart item not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteCartItemById(@PathVariable(name = "id") long id) {
        log.info("Received request to delete cart item with ID: {}", id);
        cartItemsService.deleteItemById(id);
    }

    @ExceptionHandler({InvalidCartItemException.class, CartItemNotFoundException.class})
    public ResponseEntity<String> handleCartItemException(Exception exception) {
        HttpStatus status = (exception instanceof InvalidCartItemException) ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
