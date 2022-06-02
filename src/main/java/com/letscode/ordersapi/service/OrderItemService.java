package com.letscode.ordersapi.service;

import com.letscode.ordersapi.Request.ProductRequest;
import com.letscode.ordersapi.Request.UserRequest;
import com.letscode.ordersapi.domain.Order;
import com.letscode.ordersapi.domain.OrderItem;
import com.letscode.ordersapi.gateway.ProdutoGateway;
import com.letscode.ordersapi.gateway.UserGateway;
import com.letscode.ordersapi.repository.OrderItemRepository;
import com.letscode.ordersapi.repository.OrderRepository;
import com.letscode.ordersapi.util.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private UserGateway userGateway;
    private OrderRepository orderRepository;
    private ProdutoGateway produtoGateway;

    public OrderItemService(OrderItemRepository orderItemRepository, UserGateway userGateway,OrderRepository orderRepository, ProdutoGateway produtoGateway ) {

        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userGateway = userGateway;
        this.produtoGateway = produtoGateway;
    }

    public List<OrderItem> getOrderItems(Long user_id, String order_id) {

        ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);
        Optional<Order> orderExists = this.orderRepository.findById(order_id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Usuário informado não existe");
        }

        if (orderExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Pedido informado não existe");
        }

        List<OrderItem> orderItems = this.orderItemRepository
                .findAll()
                .stream()
                .filter(orderItem -> orderItem.getOrderId().equals(orderExists.get().getId()))
                .collect(Collectors.toList());

        return orderItems;
    }

    public ResponseEntity<OrderItem> createOrderItem(OrderItem orderItem, Long user_id, String order_id, Long product_id) {

        BigDecimal totalPriceProduct = BigDecimal.valueOf(0);

        ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);
        Optional<Order> orderExists = this.orderRepository.findById(order_id);
        ResponseEntity<ProductRequest> responseProduct = this.produtoGateway.getProduct(product_id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Usuário informado não existe");
        }

        if (orderExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Pedido informado não existe");
        }
        if (!responseProduct.getStatusCode().equals(HttpStatus.OK)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Produto informado não existe");
        }

        ProductRequest product = responseProduct.getBody();

        totalPriceProduct = product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        Order orderUpdate = new Order();

        orderUpdate.setId(orderExists.get().getId());
        orderUpdate.setUserId(orderExists.get().getUserId());
        orderUpdate.setTotalPrice(orderExists.get().getTotalPrice().add(totalPriceProduct));
        orderUpdate.setTotalOrderItems(orderExists.get().getTotalOrderItems() + orderItem.getQuantity());
        orderUpdate.setStatus(orderExists.get().getStatus());
        orderUpdate.setCreatedAt(orderExists.get().getCreatedAt());
        orderUpdate.setUpdatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));

        this.orderRepository.save(orderUpdate);

        orderItem.setOrderId(orderExists.get().getId());
        orderItem.setProductId(product.getId());
        orderItem.setCreatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));
        orderItem.setUpdatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));

        this.orderItemRepository.save(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    public ResponseEntity<OrderItem> removeOrderItem(Long user_id, String order_id, String id) {

        ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);
        Optional<Order> orderExists = this.orderRepository.findById(order_id);
        Optional<OrderItem> orderItemExists = this.orderItemRepository.findById(id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Usuário informado não existe");
        }

        if (orderExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Pedido informado não existe");
        }
        if (orderItemExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Item do pedido informado não existe");
        }

        ResponseEntity<ProductRequest> responseProduct = this.produtoGateway.getProduct(orderItemExists.get().getProductId());

        ProductRequest product = responseProduct.getBody();

        BigDecimal totalPriceProduct = product.getPrice().multiply(BigDecimal.valueOf(orderItemExists.get().getQuantity()));

        Order orderUpdate = new Order();

        orderUpdate.setId(orderExists.get().getId());
        orderUpdate.setUserId(orderExists.get().getUserId());
        orderUpdate.setTotalPrice(orderExists.get().getTotalPrice().subtract(totalPriceProduct));
        orderUpdate.setTotalOrderItems(orderExists.get().getTotalOrderItems() - orderItemExists.get().getQuantity());
        orderUpdate.setStatus(orderExists.get().getStatus());
        orderUpdate.setCreatedAt(orderExists.get().getCreatedAt());
        orderUpdate.setUpdatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));

        this.orderRepository.save(orderUpdate);

        this.orderItemRepository.delete(orderItemExists.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<OrderItem> updateOrderItem(OrderItem orderItem, Long user_id, String order_id, String id) {

        ResponseEntity<UserRequest> responseUser = this.userGateway.getUser(user_id);
        Optional<Order> orderExists = this.orderRepository.findById(order_id);
        Optional<OrderItem> orderItemExists = this.orderItemRepository.findById(id);

        if (!responseUser.getStatusCode().equals(HttpStatus.OK)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Usuário informado não existe");
        }

        if (orderExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Pedido informado não existe");
        }
        if (orderItemExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Item do pedido informado não existe");
        }

        orderItem.setId(orderItemExists.get().getId());
        orderItem.setCreatedAt(orderItemExists.get().getCreatedAt());
        orderItem.setUpdatedAt(Date.from(java.time.ZonedDateTime.now().toInstant()));
        this.orderItemRepository.save(orderItem);

        return null;
    }

}
