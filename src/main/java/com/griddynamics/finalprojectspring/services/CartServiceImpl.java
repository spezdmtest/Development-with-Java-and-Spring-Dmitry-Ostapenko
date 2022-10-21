package com.griddynamics.finalprojectspring.services;

import org.springframework.stereotype.Service;
import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.dto.CartDetailDTO;
import com.griddynamics.finalprojectspring.entities.Cart;
import com.griddynamics.finalprojectspring.entities.Product;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.repositories.CartRepository;
import com.griddynamics.finalprojectspring.repositories.ProductRepository;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import static java.math.BigDecimal.ROUND_DOWN;


@Service
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

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
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


    private List<Product> getCollectProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getById)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Cart cart, List<Long> productIds) {
        List<Product> products = cart.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectProductsByIds(productIds));
        cart.setProducts(newProductList);
        cartRepository.save(cart);
    }

    @Override
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
    public CartDTO getCartByUser(String name) {

        User user = userService.findByName(name);
        if (user == null || user.getCart() == null) {
            return new CartDTO();
        }
        CartDTO cartDTO = new CartDTO();
        Map<Long, CartDetailDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getCart().getProducts();
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
        cartDTO.setCart(new ArrayList<>(mapByProductId.values()));
        cartDTO.calc();

        return cartDTO;
    }
}