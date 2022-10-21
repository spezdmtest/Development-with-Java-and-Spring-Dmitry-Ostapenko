package com.griddynamics.finalprojectspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.griddynamics.finalprojectspring.entities.Product;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailDTO {
    private Long id;
    private String title;
    private BigDecimal quantity;
    private BigDecimal total;
    private Double sum;

    public CartDetailDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.quantity = new BigDecimal("1.00");
        this.total = product.getAvailable();
        this.sum = Double.valueOf(product.getPrice());
    }
}
