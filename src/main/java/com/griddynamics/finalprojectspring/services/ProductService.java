package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import java.math.BigDecimal;
import java.util.List;


public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserCart (Long productId, String username);
    void  deleteToUserCart (Long productId, String username);
    CartDTO updateToUserCart (Long productId, BigDecimal quantity, String username);
    void addProduct(ProductDTO dto);
}
