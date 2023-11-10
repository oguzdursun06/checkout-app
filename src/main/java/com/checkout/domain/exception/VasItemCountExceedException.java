package com.checkout.domain.exception;

public class VasItemCountExceedException extends BusinessException {

    private final int maximumVasItemCount;

    public VasItemCountExceedException(int maximumVasItemCount) {
        this.maximumVasItemCount = maximumVasItemCount;
    }

    @Override
    public String toString() {
        return String.format("A single vas item can not be more than %d.", maximumVasItemCount);
    }
}
