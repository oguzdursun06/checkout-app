package com.checkout.infrastructure.persistence;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.price.Price;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryCartRepository implements CartRepository{
    
    private static final Map<Integer, Cart> cartMap = new HashMap<>();

    @PostConstruct
    void initializeMap(){
        initializeCart();
    }


    public Cart findById() {
        Cart cart = cartMap.get(1);
        return copyOfCart(cart);
    }

    public void save(Cart cart) {
        cartMap.put(1, cart);
    }

    public void clear(){
        initializeCart();
    }

    private void initializeCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(new Price(0));
        cartMap.put(1, cart);
    }

    private Cart copyOfCart(Cart cart) {
        Cart newCart = new Cart();
        newCart.setId(cart.getId());
        newCart.setItems(new ArrayList<>(cart.getItems()));
        newCart.setTotalPrice(cart.getTotalPrice());
        newCart.setTotalDiscount(cart.getTotalDiscount());
        newCart.setAppliedPromotionId(cart.getAppliedPromotionId());
        newCart.setTotalItemQuantity(cart.getTotalItemQuantity());
        return newCart;
    }

}
