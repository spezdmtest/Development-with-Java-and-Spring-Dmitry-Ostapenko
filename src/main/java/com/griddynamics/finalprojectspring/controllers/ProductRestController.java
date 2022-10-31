package com.griddynamics.finalprojectspring.controllers;

import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private ProductService productService;

    @RequestMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProductInCart(@RequestBody ProductDTO product, Principal principal) {
        productService.addToUserCart(product.getId(), principal.getName());
    }

    @RequestMapping(path = "/{id}/cart")
    public void deleteProductInCart(@PathVariable("id") Long id, Principal principal) {
        productService.deleteToUserCart(id, principal.getName());
    }

    @RequestMapping(path = "/{id}/{quantity}/cart")
    public CartDTO Cart(@PathVariable Long id,
                        @PathVariable BigDecimal quantity,
                        Principal principal) {
        return productService.updateToUserCart(id, quantity, principal.getName());
    }
}
