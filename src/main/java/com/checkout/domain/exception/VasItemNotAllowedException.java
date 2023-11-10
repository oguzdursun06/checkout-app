package com.checkout.domain.exception;


public class VasItemNotAllowedException extends BusinessException {


    public VasItemNotAllowedException() {

    }

    @Override
    public String getMessage() {
        return "Vas item can not be added to this item.";
    }
}
