package com.letscode.ordersapi.controller;

import com.letscode.ordersapi.domain.Order;
import com.letscode.ordersapi.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/{user_id}/orders")
@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<Order> getOrdersByUser(@PathVariable Long user_id) {

        List<Order> orders = this.orderService.getAllOrders(user_id);

        return orders;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrderByUser (@PathVariable Long user_id){

        Order orderSaved = this.orderService.createOrder(user_id);

        return  orderSaved;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderByUser(@PathVariable Long user_id, @PathVariable String id) {

        this.orderService.removeOrder(user_id, id);

    }

    @PatchMapping("/{id}/closed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setClosedStatusOrder(@PathVariable Long user_id, @PathVariable String id) {

        this.orderService.setClosedStatusOrder(user_id, id);

    }
    @PatchMapping("/{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setOpenStatusOrder(@PathVariable Long user_id, @PathVariable String id) {

        this.orderService.setOpenStatusOrder(user_id, id);

    }

}
