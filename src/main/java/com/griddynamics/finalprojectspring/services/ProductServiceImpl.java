package com.griddynamics.finalprojectspring.services;

import org.springframework.stereotype.Service;
import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.entities.Cart;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.mapper.ProductMapper;
import com.griddynamics.finalprojectspring.repositories.ProductRepository;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository repository;
    private final UserService userService;
    private final CartService cartService;

    public ProductServiceImpl(ProductRepository repository,
                              UserService userService,
                              CartService cartService) {
        this.repository = repository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(repository.findAll());
    }

    @Override
    @Transactional
    public void addToUserCart(Long productId, String username) {

        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found - " + username);
        }

        Cart cart = user.getCart();
        if (cart == null) {
            Cart newCart = cartService.createCart(user, Collections.singletonList(productId));
            user.setCart(newCart);
            userService.save(user);
        } else {
            cartService.addProducts(cart, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public void deleteToUserCart(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null) {
            throw new RuntimeException("User not found - " + username);
        }

        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found !");
        } else {
            cartService.deleteProducts(cart,Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public CartDTO updateToUserCart(Long productId, BigDecimal quantity, String username) {
           return cartService.updateProducts(productId, quantity, username);
    }
}


