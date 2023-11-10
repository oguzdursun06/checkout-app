package com.checkout.domain.exception;

public class ItemSellerMismatchException extends BusinessException {

    private final Long sellerId;

    public ItemSellerMismatchException(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String getMessage() {
        return String.format("Seller %d can not sell this item.", sellerId);
    }
}
