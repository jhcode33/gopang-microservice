package com.gopang.orderservice.service;

import com.gopang.orderservice.domain.OrderState;
import com.gopang.orderservice.domain.Orders;
import com.gopang.orderservice.domain.PaymentStatus;
import com.gopang.orderservice.dto.OrderRequest;
import com.gopang.orderservice.dto.item.ReqItemOrder;
import com.gopang.orderservice.dto.item.ResItemOrder;
import com.gopang.orderservice.event.OrderEvent;
import com.gopang.orderservice.message.payment.ReqPayOrder;
import com.gopang.orderservice.message.payment.ResPayOrder;
import com.gopang.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final DetailsService historyService;

    private final RestTemplate restTemplate;

    private final ApplicationEventPublisher eventPublisher;

    @Bean
    public Consumer<ResPayOrder> consumerBinding() {
        return pay -> stateUpdate(pay.getOrderId(), pay.getPaymentStatus());
    }

    // 주문 요청
    @Transactional
    public Orders register(OrderRequest request) throws Exception {
        Orders orders;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        List<ReqItemOrder> reqItemOrder = request.getReqItemOrder();

        List<Long> idList = new ArrayList<>();

        for(ReqItemOrder itemOrder : reqItemOrder) {
            idList.add(itemOrder.getItemId());
        }

        HttpEntity<List<Long>> entity = new HttpEntity<>(idList, headers);

        ParameterizedTypeReference<List<ResItemOrder>> responseType = new ParameterizedTypeReference<List<ResItemOrder>>() {};

        ResponseEntity<List<ResItemOrder>> response = restTemplate.exchange(
                "http://itemserver:8081/api/v1/order/item",
                HttpMethod.POST,
                entity,
                responseType);

        Long totalAmount = 0L;

        // 아이템 번호, 아이템 이름, 아이템 개별 금액
        List<ResItemOrder> resItemOrder = response.getBody();

        for (ReqItemOrder reqItem : reqItemOrder) {
            for (ResItemOrder resItem : resItemOrder) {
                if (reqItem.getItemId().equals(resItem.getItemId())) {
                    totalAmount += reqItem.getAmount() * resItem.getItemPrice();
                    resItem.setAmount(reqItem.getAmount());
                }
            }
        }

        if(resItemOrder.size() == 0){
            return null;
        }

        try {
            orders = Orders.builder()
                    .user_id(request.user_id)
                    .reciever_name(request.reciever_name)
                    .reciever_phone(request.reciever_phone)
                    .reciever_addr(request.reciever_addr)
                    .amount(totalAmount)
                    .deleteYn("N")
                    .build();

            orders = orderRepository.save(orders);

            historyService.register(orders.getId(), request, resItemOrder);

            ReqPayOrder payment = ReqPayOrder.builder()
                    .order_id(orders.getId().toString())
                    .amount(totalAmount.intValue())
                    .build();

            String message = payment.getOrder_id()+"번 주문 결제 요청됨.";

            eventPublisher.publishEvent(new OrderEvent(payment, message));

        } catch (Exception e) {
            throw new Exception("잘못된 요청입니다.", e);
        }
        return orders;
    }

    // 주문 상태 업데이트
    public void stateUpdate (Long orderId, PaymentStatus status) {
        System.out.println(status);
        Optional<Orders> history = orderRepository.findById(orderId);

        Orders orders;

        if (history.isPresent()) {
            orders = history.get();
            if (status == PaymentStatus.PAYCOMPLETE) {
                orders.setOrder_state(OrderState.PAYCOMPLETE);
            } else {
                orders.setOrder_state(OrderState.CANCEL);
            }

            orderRepository.save(orders);
        }
    }

    @Transactional
    public void orderCancel (Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Orders selectOrder(Long orderId) {
        return orderRepository.findById(orderId).get();
    }



}
