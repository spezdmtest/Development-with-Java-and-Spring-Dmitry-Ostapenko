package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.dto.CartDetailDTO;
import com.griddynamics.finalprojectspring.entities.Cart;
import com.griddynamics.finalprojectspring.entities.Product;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.repositories.CartRepository;
import com.griddynamics.finalprojectspring.repositories.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_DOWN;


@Service
@Data
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private boolean quantity;

    public boolean isNotQuantity() {
        return quantity;
    }

    public void setNotQuantity(boolean quantity) {
        this.quantity = quantity;
    }

    @Override
    @Transactional
    public Cart createCart(User user, List<Long> productIds) {
        Cart cart = new Cart();
        cart.setUser(user);
        List<Product> productList = getCollectProductsByIds(productIds);
        cart.setProducts(productList);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void addProducts(Cart cart, List<Long> productIds) {
        List<Product> products = cart.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectProductsByIds(productIds));
        cart.setProducts(newProductList);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteProducts(Cart cart, List<Long> productsIds) {
        List<Product> products = cart.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.removeAll(getCollectProductsByIds(productsIds));
        cart.setProducts(newProductList);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO updateProducts(Long productId, BigDecimal quantity, String name) {
        CartDTO cartByUser = getCartByUser(name);
        List<CartDetailDTO> cart = cartByUser.getCart();
        cart.stream().filter(id -> Objects.equals(productId, id.getId()))
                .forEach(newQuantity -> newQuantity.setQuantity(quantity));
        cart.stream().filter(id -> Objects.equals(productId, id.getId())).
                forEach(newSum -> newSum.setSum(newSum.getSum() * quantity.doubleValue()));
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCart(cart);
        cartDTO.calc();

        return cartDTO;
    }

    @Override
    @Transactional
    public CartDTO getCartByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getCart() == null) {
            return new CartDTO();
        }
        CartDTO cartDTO = new CartDTO();
        Map<Long, CartDetailDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getCart().getProducts();
        countProducts(products, mapByProductId);
        cartDTO.setCart(new ArrayList<>(mapByProductId.values()));
        cartDTO.calc();

        return cartDTO;
    }

    private List<Product> getCollectProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getById)
                .collect(Collectors.toList());
    }

    private void countProducts(List<Product> products, Map<Long, CartDetailDTO> mapByProductId) {
        for (Product product : products) {
            CartDetailDTO cartDetailDTO = mapByProductId.get(product.getId());
            if (cartDetailDTO == null) {
                mapByProductId.put(product.getId(), new CartDetailDTO(product));
            } else {
                cartDetailDTO.setQuantity(cartDetailDTO.getQuantity().add(new BigDecimal(1.0)).setScale(2, ROUND_DOWN));
                cartDetailDTO.setSum(cartDetailDTO.getSum() + Double.parseDouble(product.getPrice()));
                cartDetailDTO.setTotal(cartDetailDTO.getTotal());
                if (cartDetailDTO.getQuantity().compareTo(cartDetailDTO.getTotal()) > 0) {
                    setNotQuantity(true);
                }
            }
        }
    }
}