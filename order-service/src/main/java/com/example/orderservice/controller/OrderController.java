package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder orderDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

//        OrderDto createdOrder = orderService.createOrder(orderDto);
//        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        /* send this order to the kafka */
        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("orders", orderDto);

        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable String userId) {
        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orders.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
