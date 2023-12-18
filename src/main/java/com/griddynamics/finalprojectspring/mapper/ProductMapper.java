package com.griddynamics.finalprojectspring.mapper;

import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.entities.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDTO dto);

    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    List<Product> toProductList(List<ProductDTO> productDTOList);
    @InheritInverseConfiguration
    List<ProductDTO> fromProductList(List<Product> products);
}
