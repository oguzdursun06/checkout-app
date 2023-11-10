package com.checkout.domain.exception;

public class CartUniqueItemCountExceededException extends BusinessException {

    private final int maximumUniqueItemCount;

    public CartUniqueItemCountExceededException(int maximumUniqueItemCount) {
        this.maximumUniqueItemCount = maximumUniqueItemCount;
    }

    @Override
    public String getMessage() {
        return String.format("Total number of unique items in the cart can not be more than %d.", maximumUniqueItemCount);
    }
}