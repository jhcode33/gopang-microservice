package com.gopang.orderservice.controller;

import com.gopang.orderservice.dto.Details;
import com.gopang.orderservice.service.DetailsService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class DetailsController {
    private final DetailsService historyService;

    // 주문 조회시(user_id) -> 주문 내역 get 요청
    @GetMapping("/{user_id}")
//    전체 주문 내역 조회
//    수정 필요
    public ResponseEntity<List<Details.HistoryResponse>> historyList (@PathVariable Long user_id) {
        List<Details.HistoryResponse> allHistory = historyService.getAllHistory(user_id);

        for (Details.HistoryResponse history: allHistory) {
            System.out.println(history);
        }

        return new ResponseEntity<>(allHistory, HttpStatus.OK);
    }

    @GetMapping("/{order_id}/receipt")
//    전체 주문 내역 조회
//    수정 필요
    public ResponseEntity<Details.HistoryResponse> historyReceipt (@PathVariable Long order_id) {
        // 상품명, 수량, 거래액
        return null;
    }
}
