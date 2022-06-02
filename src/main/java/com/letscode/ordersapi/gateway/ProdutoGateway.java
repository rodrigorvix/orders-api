package com.letscode.ordersapi.gateway;

import com.letscode.ordersapi.Request.ProductRequest;
import com.letscode.ordersapi.Request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProdutoGateway {
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<ProductRequest> getProduct(Long id) {
        String url = String.format("http://products-api-instance:8080/v1/products/%s",id);
        return restTemplate.getForEntity(url, ProductRequest.class);

    }
}
