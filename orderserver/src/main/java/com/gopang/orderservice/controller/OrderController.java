package com.gopang.orderservice.controller;

import com.gopang.orderservice.domain.Orders;
import com.gopang.orderservice.dto.OrderRequest;
import com.gopang.orderservice.event.OrderCancelEvent;
import com.gopang.orderservice.message.payment.ReqPayOrder;
import com.gopang.orderservice.service.DetailsService;
import com.gopang.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping
    // 주문 요청
    public ResponseEntity<?> order(@RequestBody OrderRequest order) throws Exception {
        orderService.register(order);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 주문 취소 요청
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> orderCancel(@PathVariable Long orderId) throws Exception {
        try{
            String message = orderId+"번 주문 결제취소 요청됨.";

            Orders order = orderService.selectOrder(orderId);

            ReqPayOrder payment = ReqPayOrder.builder()
                    .order_id(order.getId().toString())
                    .amount(order.amount.intValue())
                    .build();

            // 결제 요청
            eventPublisher.publishEvent(new OrderCancelEvent(payment, message));

            orderService.orderCancel(orderId);
        } catch (Exception e) {
            throw new Exception("요청중 오류 발생");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
