package com.checkout.infrastructure.messaging;

import com.checkout.application.event.CartUpdatedEvent;
import com.checkout.application.event.PromotionAppliedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ApplicationEventPublisherAdapter implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ApplicationEventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishCartUpdated(CartUpdatedEvent cartUpdatedEvent) {
        applicationEventPublisher.publishEvent(cartUpdatedEvent);
    }

    @Override
    public void publishPromotionApplied(PromotionAppliedEvent promotionAppliedEvent) {
        applicationEventPublisher.publishEvent(promotionAppliedEvent);
    }
}
