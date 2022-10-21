package com.griddynamics.finalprojectspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.griddynamics.finalprojectspring.dto.CartDTO;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.services.ProductService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public void setService(ProductService service) {this.productService = service;}

    @GetMapping(path = "/list", produces = "application/json")
    public List<ProductDTO> getAll() {return productService.getAll();}

    @PostMapping(consumes = "application/json")
    public void addProductInCart(@RequestBody ProductDTO product, Principal principal) {
        productService.addToUserCart(product.getId(), principal.getName());
    }

    @GetMapping(path = "/{id}/cart")
    public void deleteProductInCart(@PathVariable("id") Long id, Principal principal) {
        productService.deleteToUserCart(id, principal.getName());
    }

    @GetMapping(path = "/{id}/{quantity}/cart")
    public CartDTO Cart(@PathVariable Long id,
                     @PathVariable BigDecimal quantity,
                     Principal principal) {
        return productService.updateToUserCart(id, quantity, principal.getName());
    }
}
