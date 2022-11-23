package com.griddynamics.finalprojectspring.mapper;

import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.dto.ProductDTO.ProductDTOBuilder;
import com.griddynamics.finalprojectspring.entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T09:48:32+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.3 (JetBrains s.r.o)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( dto.getId() );
        product.setTitle( dto.getTitle() );
        product.setAvailable( dto.getAvailable() );
        product.setPrice( dto.getPrice() );

        return product;
    }

    @Override
    public ProductDTO fromProduct(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTOBuilder productDTO = ProductDTO.builder();

        productDTO.id( product.getId() );
        productDTO.title( product.getTitle() );
        productDTO.available( product.getAvailable() );
        productDTO.price( product.getPrice() );

        return productDTO.build();
    }

    @Override
    public List<Product> toProductList(List<ProductDTO> productDTOList) {
        if ( productDTOList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productDTOList.size() );
        for ( ProductDTO productDTO : productDTOList ) {
            list.add( toProduct( productDTO ) );
        }

        return list;
    }

    @Override
    public List<ProductDTO> fromProductList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( products.size() );
        for ( Product product : products ) {
            list.add( fromProduct( product ) );
        }

        return list;
    }
}
