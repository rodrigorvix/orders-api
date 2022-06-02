package com.letscode.ordersapi.domain;

import com.letscode.ordersapi.util.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;

@Document(collection = "orders")
@Data
public class Order {

    @Id
    private String id;

    private BigDecimal totalPrice;

    private int totalOrderItems;

    private OrderStatus status;

    private Long userId;

    private Date createdAt;

    private Date updatedAt;
}
