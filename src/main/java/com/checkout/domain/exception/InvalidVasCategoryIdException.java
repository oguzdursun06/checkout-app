package com.checkout.domain.exception;

public class InvalidVasCategoryIdException extends BusinessException {

    private final Long categoryId;

    public InvalidVasCategoryIdException(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String getMessage() {
        return String.format("Id %d is not vas item category.", categoryId);
    }

}