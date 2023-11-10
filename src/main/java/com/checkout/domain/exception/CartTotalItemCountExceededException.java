package com.checkout.domain.exception;

public class CartTotalItemCountExceededException extends BusinessException {

    private final int maximumTotalItemCount;

    public CartTotalItemCountExceededException(int maximumTotalItemCount) {
        this.maximumTotalItemCount = maximumTotalItemCount;
    }


    @Override
    public String getMessage() {
        return String.format("Total number of items in the cart can not be more than %d.", maximumTotalItemCount);
    }
}