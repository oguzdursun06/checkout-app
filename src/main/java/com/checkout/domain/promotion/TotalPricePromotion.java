package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.price.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TotalPricePromotion implements Promotion{

    private final Long id;

    private final double smallPrice;

    private final double smallDiscount;

    private final double mediumPrice;

    private final double mediumDiscount;

    private final double largePrice;

    private final double largeDiscount;

    private final double xlargeDiscount;


    public TotalPricePromotion(@Value("${application.promotion.totalPrice.id}") Long id,
                               @Value("${application.promotion.totalPrice.small.price}") double smallPrice,
                               @Value("${application.promotion.totalPrice.small.discount}") double smallDiscount,
                               @Value("${application.promotion.totalPrice.medium.price}") double mediumPrice,
                               @Value("${application.promotion.totalPrice.medium.discount}") double mediumDiscount,
                               @Value("${application.promotion.totalPrice.large.price}") double largePrice,
                               @Value("${application.promotion.totalPrice.large.discount}") double largeDiscount,
                               @Value("${application.promotion.totalPrice.xlarge.discount}") double xlargeDiscount) {
        this.id = id;
        this.smallPrice = smallPrice;
        this.smallDiscount = smallDiscount;
        this.mediumPrice = mediumPrice;
        this.mediumDiscount = mediumDiscount;
        this.largePrice = largePrice;
        this.largeDiscount = largeDiscount;
        this.xlargeDiscount = xlargeDiscount;
    }
    @Override
    public double calculateDiscount(Cart cart) {
        Price cartTotalPrice = cart.getTotalPrice();
        double totalPriceAmount = cartTotalPrice.getAmount();

        if(totalPriceAmount < smallPrice){
            return smallDiscount;
        }

        if(totalPriceAmount < mediumPrice){
            return mediumDiscount;
        }

        if(totalPriceAmount < largePrice){
            return largeDiscount;
        }

        return xlargeDiscount;
    }

    @Override
    public Long getId() {
        return id;
    }
}
