package com.checkout.domain.exception;

public class CartTotalPriceExceededException extends BusinessException {

    private final double maximumTotalPrice;

    public CartTotalPriceExceededException(double maximumTotalPrice) {
        this.maximumTotalPrice = maximumTotalPrice;
    }


    @Override
    public String getMessage() {
        return String.format("Cart total price can not be more than %d TL.", (int) maximumTotalPrice);
    }
}