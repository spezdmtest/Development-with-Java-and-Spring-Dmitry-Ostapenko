package com.griddynamics.finalprojectspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal available;
    private String price;
}
