package com.gopang.orderservice.service;

import com.gopang.orderservice.domain.OrderDetails;
import com.gopang.orderservice.domain.Orders;
import com.gopang.orderservice.dto.Details;
import com.gopang.orderservice.dto.OrderRequest;
import com.gopang.orderservice.dto.item.ResItemOrder;
import com.gopang.orderservice.event.CancelResEvent;
import com.gopang.orderservice.message.payment.CancelPayOrder;
import com.gopang.orderservice.repository.DetailsRepository;
import com.gopang.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailsService {
    private final DetailsRepository detailsRepository;

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Bean
    public Consumer<CancelPayOrder> cancelBinding() {
        return cancel -> {
            log.info(String.valueOf(cancel));
            String message = cancel.getMerchant_Uid()+"번 주문 취소 이벤트 수신됨";
            eventPublisher.publishEvent(new CancelResEvent(message,cancel));
        };
    }


    public void register (Long order_id, OrderRequest request, List<ResItemOrder> resItemOrders) {
        Orders order = orderRepository.findById(order_id).get();
        for(ResItemOrder item : resItemOrders) {
            OrderDetails history = OrderDetails.builder()
                    .orders(order)
                    .user_id(request.user_id)
                    .item_id(item.getItemId())
                    .item_name(item.getItemName())
                    .price(item.getItemPrice())
                    .amount(item.getAmount())
                    .totalPrice(item.getItemPrice() * item.getAmount())
                    .build();

            detailsRepository.save(history);
        }
    }



//    전체 주문 내역 조회
//    수정 필요
    @Transactional(readOnly = true)
    public List<Details.HistoryResponse> getAllHistory (Long userId) {
        List<OrderDetails> allByUserId = detailsRepository.findAllByUserId(userId);
        return allByUserId.stream().map(orderHistory ->
                Details.HistoryResponse.builder()
                        .id(orderHistory.getId())
                        .user_id(orderHistory.getUser_id())
                        .item_id(orderHistory.getItem_id())
                        .order_amount(orderHistory.getPrice())
                        .orders(orderHistory.getOrders())
                        .build()
        ).toList();
    }

}
