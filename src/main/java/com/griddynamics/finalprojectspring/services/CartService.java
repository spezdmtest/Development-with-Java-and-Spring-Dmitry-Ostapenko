package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.entities.Cart;
import com.griddynamics.finalprojectspring.entities.User;
import java.math.BigDecimal;
import java.util.List;


public interface CartService {
    Cart createCart(User user, List<Long> productIds);

    void addProducts(Cart cart, List<Long> productIds);

    CartDTO getCartByUser(String name) throws Exception;

    boolean isNotQuantity();

    void deleteProducts(Cart cart, List<Long> productIds);

    CartDTO updateProducts(Long productId, BigDecimal quantity, String name);
}