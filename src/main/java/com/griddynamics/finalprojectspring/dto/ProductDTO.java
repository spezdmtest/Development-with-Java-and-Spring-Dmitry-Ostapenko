package com.griddynamics.finalprojectspring.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal available;
    private String price;
}
