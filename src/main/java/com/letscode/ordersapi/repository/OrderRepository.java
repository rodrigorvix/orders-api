package com.letscode.ordersapi.repository;

import com.letscode.ordersapi.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
