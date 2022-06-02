package com.letscode.ordersapi.repository;

import com.letscode.ordersapi.domain.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
}
