package com.griddynamics.finalprojectspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private String order = "Successful order";
    private int amount;
    private Double sum;

    public List<CartDetailDTO> cart = new ArrayList<>();

    public Double calc() {
        this.amount = cart.size();
        return this.sum = cart.stream()
                .map(CartDetailDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
