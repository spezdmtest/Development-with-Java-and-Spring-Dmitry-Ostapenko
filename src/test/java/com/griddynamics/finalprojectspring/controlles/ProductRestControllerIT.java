package com.griddynamics.finalprojectspring.controlles;

import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProductService productService;
    private ProductDTO expectedProduct = new ProductDTO(99L, "Test Product", BigDecimal.TEN, "150.00");
    private List<ProductDTO> listOfProduct = new ArrayList<>();

    @BeforeEach
    void setUp() {
        given(productService.getAll()).willReturn(listOfProduct);
    }

    @Test
    void getAll() {
        //execute
        listOfProduct.add(ProductDTO.builder().id(1L).title("Product_Test").available(BigDecimal.valueOf(2.00))
                .price("20.00").build());
        listOfProduct.add(ProductDTO.builder().id(2L).title("Product_Test2").available(BigDecimal.valueOf(2.00))
                .price("25.00").build());
        ResponseEntity<Void> response =
                restTemplate.withBasicAuth("my@email.com", "123")
                        .getForEntity("/api/v1/products/list", Void.class);
        //check
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService).getAll();
    }

    @Test
    void addProductInCart() {
        //execute
        ResponseEntity<Void> response =
                restTemplate.withBasicAuth("my@email.com", "123")
                        .postForEntity("/api/v1/products/", expectedProduct, Void.class);
        //check
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService).addToUserCart(Mockito.eq(99L), Mockito.eq("my@email.com"));
    }
}
