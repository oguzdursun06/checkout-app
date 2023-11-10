package com.checkout.domain.cart;

import com.checkout.domain.exception.ItemNotFoundException;
import com.checkout.domain.price.Price;
import com.checkout.domain.item.Item;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private Long id = 1L;

    private List<Item> items = new ArrayList<>();

    private Price totalPrice;

    private Long appliedPromotionId;

    private double totalDiscount;

    private int totalItemQuantity;


    public void addItemToCart(Item addedItem){
        double itemPrice = addedItem.getQuantity() * addedItem.getPrice();
        addedItem.setTotalPrice(itemPrice);
        items.add(addedItem);
        increaseTotalPrice(itemPrice);
        increaseTotalItemQuantity(addedItem.getQuantity());
    }

    public void removeItemFromCart(Long itemId) {
        Item item = getItemById(itemId);
        items.remove(item);
        double itemQuantity = item.getQuantity();
        double itemPrice = item.getPrice();
        decreaseTotalPrice(itemPrice);
        decreaseTotalItemQuantity(itemQuantity);
    }

    public void resetCart(){
        setItems(new ArrayList<>());
        setTotalPrice(new Price(0));
        setAppliedPromotionId(null);
        setTotalItemQuantity(0);
        setTotalDiscount(0);
    }

    public Item getItemById(Long itemId) {
        return items.stream()
                .filter(item -> item.getItemId().equals(itemId)).findFirst()
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    public boolean containsItem(Long itemId){
        return items.stream()
                .anyMatch(item -> item.getItemId().equals(itemId));
    }

    public boolean checkPromotionCategoryIdExist(Long categoryId){
        return items.stream()
                .anyMatch(item -> item.getCategoryId().equals(categoryId));
    }

    public void setTotalDiscount(double totalDiscount) {
        if(totalPrice.getAmount() >= totalDiscount){
            this.totalDiscount = totalDiscount;
        }
    }

    private void increaseTotalItemQuantity(double itemQuantity) {
        totalItemQuantity += itemQuantity;
    }

    private void decreaseTotalItemQuantity(double itemQuantity) {
        totalItemQuantity -= itemQuantity;
    }

    public void increaseTotalPrice(double increaseAmount) {
        double increasedPrice = totalPrice.getAmount() + increaseAmount;
        this.totalPrice = totalPrice.changeAmount(increasedPrice);
    }

    private void decreaseTotalPrice(double decreaseAmount) {
        double decreasedPrice = totalPrice.getAmount() - decreaseAmount;
        this.totalPrice = totalPrice.changeAmount(decreasedPrice);
    }

}
