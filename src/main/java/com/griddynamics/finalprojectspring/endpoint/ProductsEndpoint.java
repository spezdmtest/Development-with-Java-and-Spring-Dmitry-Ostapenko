package com.griddynamics.finalprojectspring.endpoint;

import com.griddynamics.finalprojectspring.config.WebServiceConfig;
import com.griddynamics.finalprojectspring.dto.ProductDTO;
import com.griddynamics.finalprojectspring.services.ProductService;
import com.griddynamics.finalprojectspring.ws.com.griddynamics.finalprojectspring.ws.GetProductsRequest;
import com.griddynamics.finalprojectspring.ws.com.griddynamics.finalprojectspring.ws.GetProductsResponse;
import com.griddynamics.finalprojectspring.ws.com.griddynamics.finalprojectspring.ws.ProductsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductsEndpoint {

    private ProductService productService;

    @Autowired
    public ProductsEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_PRODUCTS, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProductWS(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        productService.getAll()
                .forEach(dto -> response.getProducts().add(createProductWs(dto)));
        return response;
    }

    private ProductsWS createProductWs(ProductDTO dto) {
        ProductsWS ws = new ProductsWS();
        ws.setId(dto.getId());
        ws.setTitle(dto.getTitle());
        ws.setPrice(dto.getPrice());
        return ws;
    }
}
