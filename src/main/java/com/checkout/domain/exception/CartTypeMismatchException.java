package com.checkout.domain.exception;

public class CartTypeMismatchException extends BusinessException{

    public CartTypeMismatchException() {

    }

    @Override
    public String getMessage() {
        return "Given item type can not be added to this cart due to the different types.";
    }
}
