package com.checkout.domain.exception;

public class ItemAlreadyExistException extends BusinessException {

    private final long itemId;
    public ItemAlreadyExistException(long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getMessage() {
        return String.format("Item id %d already exist in the cart, can not be added again.", itemId);
    }
}
