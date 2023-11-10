package com.checkout.domain.exception;

public class ItemNotFoundException extends BusinessException {

    private final Long itemId;

    public ItemNotFoundException(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getMessage() {
        return String.format("There is no item with id %d in the cart.", itemId);
    }
}
