package com.library.service.impl;

import com.library.model.CartItem;
import com.library.model.Customer;
import com.library.model.Product;
import com.library.model.ShoppingCart;
import com.library.repository.CartItemRepository;
import com.library.repository.CustomerRepository;
import com.library.repository.ShoppingCartRepository;
import com.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;


    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) {
            cart = new ShoppingCart();
        }

        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = findCartItem(cartItemList, product.getId());
        if (cartItemList == null) {
            if (cartItem == null) {
                cartItemList = new HashSet<>();
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setShoppingCart(cart);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItemList = new HashSet<>();
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setShoppingCart(cart);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + quantity * product.getCostPrice());
                cartItemRepository.save(cartItem);
            }


        }
        cart.setCartItems(cartItemList);
        double totalPrice = totalPrice(cart.getCartItems());
        int totalQuantity = totalItems(cart.getCartItems());
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalQuantity);
        cart.setCustomer(customer);

        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart removeItemFromCart(Product product, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem cartItem = findCartItem(cartItems, product.getId());

        cartItems.remove(cartItem);

        cartItemRepository.delete(cartItem);

        cart.setTotalPrice(totalPrice(cartItems));
        cart.setTotalItems(totalItems(cartItems));
        cart.setCartItems(cartItems);

        return shoppingCartRepository.save(cart) ;
    }

    @Override
    public ShoppingCart updateItemInCart(Product product, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();

        Set<CartItem> cartItemList = cart.getCartItems();

        CartItem cartItem = findCartItem(cartItemList, product.getId());

        cartItem.setTotalPrice(quantity * product.getCostPrice());
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        cart.setTotalPrice(totalPrice(cartItemList));
        cart.setTotalItems(totalItems(cartItemList));


        return shoppingCartRepository.save(cart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem cartItemFound : cartItems) {
            if (Objects.equals(cartItemFound.getProduct().getId(), productId)) {
                cartItem = cartItemFound;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;
        for (CartItem cartItem : cartItems) {
            totalItems += cartItem.getQuantity();
        }
        return totalItems;
    }

    private double totalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotalPrice();

        }
        return totalPrice;
    }
}
