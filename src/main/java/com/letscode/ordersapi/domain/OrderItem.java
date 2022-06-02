package com.letscode.ordersapi.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "order_items")
@Data
public class OrderItem {
    @Id
    private String id;

    private Integer quantity;

    private Long productId;

    private String orderId;

    private Date createdAt;

    private Date updatedAt;
}
