package com.letscode.ordersapi.controller;

import com.letscode.ordersapi.domain.OrderItem;
import com.letscode.ordersapi.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/{user_id}/orders/{order_id}/order_items")
@RestController
public class OrderItemController {

    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping()
    public List<OrderItem> getOrderItemsByOrder(@PathVariable Long user_id, @PathVariable String order_id) {

        List<OrderItem> orderItems = this.orderItemService.getOrderItems(user_id, order_id);

        return orderItems;
    }

    @PostMapping("/{product_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderItem> createOrderItemByOrder (@RequestBody OrderItem orderItem,
                                                             @PathVariable Long user_id,
                                                             @PathVariable String order_id,
                                                             @PathVariable Long product_id){

        ResponseEntity<OrderItem> orderItemSaved = this.orderItemService.createOrderItem(orderItem, user_id, order_id, product_id);

        return  orderItemSaved;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItemByOrder(@PathVariable Long user_id, @PathVariable String order_id, @PathVariable String id) {

        this.orderItemService.removeOrderItem(user_id,order_id, id);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderItemByOrder(
            @RequestBody OrderItem orderItem,
            @PathVariable Long user_id,
            @PathVariable String order_id,
            @PathVariable String id) {

        this.orderItemService.updateOrderItem(orderItem,user_id,order_id, id);

    }
}
