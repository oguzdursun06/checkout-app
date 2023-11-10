package com.checkout.domain.cart;

import com.checkout.application.event.PromotionAppliedEvent;
import com.checkout.domain.exception.CartTotalItemCountExceededException;
import com.checkout.domain.exception.CartTypeMismatchException;
import com.checkout.domain.exception.CartUniqueItemCountExceededException;
import com.checkout.domain.exception.ItemAlreadyExistException;
import com.checkout.domain.vas.VasItem;
import com.checkout.domain.item.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final int maximumUniqueItemCount;

    private final int maximumTotalItemCount;
    
    CartService(@Value("${application.cart.maximumUniqueItemCount}") int maximumUniqueItemCount,
                @Value("${application.cart.maximumTotalItemCount}") int maximumTotalItemCount){
        this.maximumUniqueItemCount = maximumUniqueItemCount;
        this.maximumTotalItemCount = maximumTotalItemCount;
    }

    public void addItemToCart(Cart cart, Item addedItem) {
        checkAdditionValidity(cart, addedItem);
        cart.addItemToCart(addedItem);
    }

    private void checkAdditionValidity(Cart cart, Item addedItem) {
        validateCartAndItem(cart, addedItem);
        validateTotalItemCountExceed(cart,addedItem);
        validateAlreadyContains(cart, addedItem);
        validateMaximumUniqueItemCountExceed(cart);
    }

    public void removeItemFromCart(Cart cart, Long itemId) {
        cart.removeItemFromCart(itemId);
    }

    public void increaseTotalPrice(Cart cart, VasItem vasItem){
        int quantity = vasItem.getQuantity();
        double price = vasItem.getPrice();
        double totalPrice = quantity * price;
        cart.increaseTotalPrice(totalPrice);
    }

    public Item getItemById(Cart cart, Long itemId) {
        return cart.getItemById(itemId);
    }

    private void validateCartAndItem(Cart cart, Item item) {
        List<Item> cartItems = cart.getItems();
        if(!cartItems.isEmpty() && !cartItems.get(0).getClass().equals(item.getClass())){
            throw new CartTypeMismatchException();
        }
    }

    private void validateMaximumUniqueItemCountExceed(Cart cart) {
        if(isMaximumUniqueItemCountExceeded(cart)){
            throw new CartUniqueItemCountExceededException(maximumUniqueItemCount);
        }
    }

    private boolean isMaximumUniqueItemCountExceeded(Cart cart) {
        return cart.getItems().size() >= maximumUniqueItemCount;
    }

    private void validateAlreadyContains(Cart cart, Item addedItem) {
        Long addedItemId = addedItem.getItemId();
        if(cart.containsItem(addedItemId)){
            throw new ItemAlreadyExistException(addedItemId);
        }
    }

    private void validateTotalItemCountExceed(Cart cart, Item addedItem) {
        int addedItemQuantity = addedItem.getQuantity();
        double currentItemQuantity = cart.getTotalItemQuantity();

        if(isTotalItemCountExceeded(currentItemQuantity, addedItemQuantity)){
            throw new CartTotalItemCountExceededException(maximumTotalItemCount);
        }
    }

    private boolean isTotalItemCountExceeded(double currentItemQuantity, double addedItemQuantity) {
        return (currentItemQuantity + addedItemQuantity) > maximumTotalItemCount;
    }

    @EventListener
    public void handlePromotionAppliedEvent(PromotionAppliedEvent promotionAppliedEvent) {
        Cart cart = promotionAppliedEvent.getCart();
        Long appliedPromotionId = promotionAppliedEvent.getAppliedPromotionId();
        double totalDiscount = promotionAppliedEvent.getTotalDiscount();
        cart.setAppliedPromotionId(appliedPromotionId);
        cart.setTotalDiscount(totalDiscount);
    }

}
