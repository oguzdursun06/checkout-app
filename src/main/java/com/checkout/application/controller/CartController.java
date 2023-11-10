package com.checkout.application.controller;

import com.checkout.application.dto.*;
import com.checkout.application.mapper.CartMapper;
import com.checkout.application.CartApplicationService;
import com.checkout.domain.cart.Cart;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class CartController {

    private final CartApplicationService cartApplicationService;

    public CartController(CartApplicationService cartApplicationService) {
        this.cartApplicationService = cartApplicationService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody @Valid ItemAddDTO itemAddDTO){
        cartApplicationService.addItemToCart(itemAddDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Item added to cart successfully!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/cart/items")
    public ResponseEntity<ApiResponse> removeItemFromCard(@RequestBody @Valid ItemRemoveDTO itemRemoveDTO){
        cartApplicationService.removeItemFromCard(itemRemoveDTO.getItemId());
        return new ResponseEntity<>(new ApiResponse(true, "Item removed from cart successfully!"), HttpStatus.OK);
    }

    @PostMapping("/cart/items/vas-items")
    public ResponseEntity<ApiResponse> addVasItemToItem(@RequestBody @Valid VasItemAddDTO vasItemAddDTO){
        cartApplicationService.addVasItemToItem(vasItemAddDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Vas item added to item successfully!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<ApiResponse> resetCart(){
        cartApplicationService.resetCart();
        return new ResponseEntity<>(new ApiResponse(true, "Cart has been reset successfully!"), HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponse> displayCart(){
        Cart cart = cartApplicationService.displayCart();
        CartMapper cartMapper = new CartMapper();
        CartDisplayDTO cartDisplayDTO = cartMapper.cartToDisplayCartDTO(cart);
        return new ResponseEntity<>(new CartResponse(true, cartDisplayDTO), HttpStatus.OK);
    }
}
