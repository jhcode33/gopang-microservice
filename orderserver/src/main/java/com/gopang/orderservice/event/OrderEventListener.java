package com.gopang.orderservice.event;

import com.gopang.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final StreamBridge streamBridge;

    private final OrderService orderService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionAfterCommit(OrderEvent event) {
        log.info("Recieved message to paymentRequest-topic : " + event.getMessage());

        streamBridge.send("paymentRequest-topic", MessageBuilder
                .withPayload(event.getReqPayOrder())
                .build()
        );
    }

    @EventListener
    public void handleTransactionBeforeCommit (OrderCancelEvent cancelEvent) {
        log.info("Recieved message to paymentCancelRequest-topic : " + cancelEvent.getMessage());
        streamBridge.send("paymentCancelRequest-topic", MessageBuilder
                .withPayload(cancelEvent.getReqPayOrder())
                .build()
        );
    }

    @EventListener
    public void handleOrderCancelListener (CancelResEvent cancelResEvent) {
        log.info(cancelResEvent.getMessage());
        String cancelStatus = cancelResEvent.getCancelPayOrder().getStatus();
        if(cancelStatus.equals("cancelled")) {
            // 재고 원복 카프카 메시지 발행
            log.info("결제 취소 성공");
            orderService.orderCancel(Long.valueOf(cancelResEvent.getCancelPayOrder().getMerchant_Uid()));
        } else {
            log.info("결제 취소 실패");
        }
        // 상태에 따른 재고관련 카프카 메시지 발행
    }

}
