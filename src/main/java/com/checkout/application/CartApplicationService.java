package com.checkout.application;

import com.checkout.application.dto.ItemAddDTO;
import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.application.event.CartUpdatedEvent;
import com.checkout.infrastructure.messaging.EventPublisher;
import com.checkout.infrastructure.persistence.CartRepository;
import com.checkout.domain.cart.Cart;
import com.checkout.domain.cart.CartService;
import com.checkout.domain.item.Item;
import com.checkout.domain.item.ItemService;
import com.checkout.domain.vas.VasItem;
import com.checkout.domain.vas.VasItemService;
import org.springframework.stereotype.Service;


@Service
public class CartApplicationService {

    private final CartRepository inMemoryCartRepository;
    private final CartService cartService;
    private final ItemService itemService;
    private final VasItemService vasItemService;
    private final EventPublisher eventPublisher;

    public CartApplicationService(CartRepository inMemoryCartRepository, CartService cartService, ItemService itemService, VasItemService vasItemService, EventPublisher eventPublisher) {
        this.inMemoryCartRepository = inMemoryCartRepository;
        this.cartService = cartService;
        this.itemService = itemService;
        this.vasItemService = vasItemService;
        this.eventPublisher = eventPublisher;
    }

    public void addItemToCart(ItemAddDTO itemAddDTO) {
        Item item = itemService.createItemById(itemAddDTO);
        Cart cart = inMemoryCartRepository.findById();
        cartService.addItemToCart(cart, item);
        eventPublisher.publishCartUpdated(new CartUpdatedEvent(cart));
        inMemoryCartRepository.save(cart);
    }

    public void removeItemFromCard(Long itemId) {
        Cart cart = inMemoryCartRepository.findById();
        cartService.removeItemFromCart(cart, itemId);
        eventPublisher.publishCartUpdated(new CartUpdatedEvent(cart));
        inMemoryCartRepository.save(cart);
    }

    public void addVasItemToItem(VasItemAddDTO vasItemAddDTO) {
        Cart cart = inMemoryCartRepository.findById();
        Item cartItem = cartService.getItemById(cart, vasItemAddDTO.getItemId());
        VasItem vasItem = vasItemService.createVasItem(vasItemAddDTO);
        itemService.addVasItemToItem(cartItem, vasItem);
        cartService.increaseTotalPrice(cart, vasItem);
        eventPublisher.publishCartUpdated(new CartUpdatedEvent(cart));
        inMemoryCartRepository.save(cart);
    }

    public void resetCart() {
        Cart cart = inMemoryCartRepository.findById();
        cart.resetCart();
        inMemoryCartRepository.save(cart);
    }

    public Cart displayCart() {
        return inMemoryCartRepository.findById();
    }
}
