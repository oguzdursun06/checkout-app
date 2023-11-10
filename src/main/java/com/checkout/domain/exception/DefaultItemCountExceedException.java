package com.checkout.domain.exception;

public class DefaultItemCountExceedException extends BusinessException {

    private final int maximumDefaultItemCount;

    public DefaultItemCountExceedException(int maximumDefaultItemCount) {
        this.maximumDefaultItemCount = maximumDefaultItemCount;
    }

    @Override
    public String getMessage() {
        return String.format("A single default item can not be more than %d.", maximumDefaultItemCount);
    }
}