package com.checkout.infrastructure.messaging;

import com.checkout.application.event.CartUpdatedEvent;
import com.checkout.application.event.PromotionAppliedEvent;

public interface EventPublisher {

    void publishCartUpdated(CartUpdatedEvent cartUpdatedEvent);

    void publishPromotionApplied(PromotionAppliedEvent promotionAppliedEvent);
}
