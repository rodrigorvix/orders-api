package com.letscode.ordersapi.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

}
