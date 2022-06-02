package com.letscode.ordersapi.gateway;

import com.letscode.ordersapi.Request.UserRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserGateway {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<UserRequest> getUser(Long id) {
        String url = String.format("http://users-api-instance:8081/v1/users/%s",id);
        return restTemplate.getForEntity(url, UserRequest.class);

    }
}
