package com.letscode.ordersapi.service;

import com.letscode.ordersapi.domain.Order;
import com.letscode.ordersapi.Request.UserRequest;
import com.letscode.ordersapi.gateway.UserGateway;
import com.letscode.ordersapi.repository.OrderRepository;
import com.letscode.ordersapi.util.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserGateway userGateway;

    public OrderService(OrderRepository orderRepository, UserGateway userGateway) {

        this.orderRepository = orderRepository;
        this.userGateway = userGateway;
    }

    public List<Order> getAllOrders(Long user_id) {

       ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            return (List<Order>) ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

            List<Order> orders = this.orderRepository
                    .findAll()
                    .stream()
                    .filter(order -> order.getUserId() == user_id)
                    .collect(Collectors.toList());

            return orders;
    }

    public Order createOrder( Long user_id) {

        ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
            Order order = new Order();

            order.setUserId(user_id);
            order.setTotalPrice(BigDecimal.valueOf(0));
            order.setStatus(OrderStatus.OPEN);
            order.setCreatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));
            order.setUpdatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));

            return this.orderRepository.save(order);

    }

    public void removeOrder(Long user_id, String id) {

        ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        }
            this.orderRepository.findById(id)
                    .map( orderExists -> {
                        this.orderRepository.delete(orderExists);
                        return orderExists;
                    })
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    public void setClosedStatusOrder(Long user_id, String id) {

            this.orderRepository.findById(id)
                    .map( orderExists -> {
                        orderExists.setStatus(OrderStatus.CLOSED);
                        this.orderRepository.save(orderExists);
                        return orderExists;
                    })
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    public void setOpenStatusOrder(Long user_id, String id) {

            this.orderRepository.findById(id)
                    .map( orderExists -> {
                        orderExists.setStatus(OrderStatus.OPEN);
                        this.orderRepository.save(orderExists);
                        return orderExists;
                    })
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }
}
