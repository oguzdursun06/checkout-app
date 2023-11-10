package com.checkout.domain.exception;


public class DigitalItemCountExceedException extends BusinessException {

    private final int maximumDigitalItemCount;

    public DigitalItemCountExceedException(int maximumDigitalItemCount) {
        this.maximumDigitalItemCount = maximumDigitalItemCount;
    }

    @Override
    public String getMessage() {
        return String.format("A single digital item can not be more than %d.", maximumDigitalItemCount);
    }
}