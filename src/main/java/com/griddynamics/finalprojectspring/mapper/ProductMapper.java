package com.griddynamics.finalprojectspring.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.entities.Product;
import java.util.List;


@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDTO dto);

    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    List<Product> toProductList(List<ProductDTO> productDTOList);

    List<ProductDTO> fromProductList(List<Product> products);
}
