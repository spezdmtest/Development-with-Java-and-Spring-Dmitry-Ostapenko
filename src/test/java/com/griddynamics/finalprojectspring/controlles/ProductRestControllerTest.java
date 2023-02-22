package com.griddynamics.finalprojectspring.controlles;

import com.griddynamics.finalprojectspring.controllers.ProductRestController;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private ProductService productService;
    private List<ProductDTO> listOfProduct = new ArrayList<>();

    @Test
    void getProductAll() throws Exception {
        listOfProduct.add(ProductDTO.builder().id(1L).title("Product_Test").available(BigDecimal.valueOf(2.00))
                .price("20.00").build());
        listOfProduct.add(ProductDTO.builder().id(2L).title("Product_Test2").available(BigDecimal.valueOf(2.00))
                .price("25.00").build());
        given(productService.getAll()).willReturn(listOfProduct);

        ResultActions response = mockMVC.perform(get("/api/v1/products/list")
                .with(user("my@email.com").password("123")));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfProduct.size())));
    }
}