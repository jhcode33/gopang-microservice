package com.gopang.paymentserver.controller;

import com.gopang.paymentserver.Service.Payment_Cancel;
import com.gopang.paymentserver.Service.Payment_Detail;
import com.gopang.paymentserver.Service.Payment_Start;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private Payment_Start paymentService;
    @Autowired
    private Payment_Detail paymentDetailService;
    @Autowired
    private Payment_Cancel paymentCancelService;


    @PostMapping("/pay")
    public String processPayment(String message) throws JSONException, IOException {
        return paymentService.Payment(message);
    }

    @GetMapping("/detail")
    public String getPaymentDetail(String message) throws IOException, JSONException {
        return paymentDetailService.PaymentDetail(message);
    }

    @PostMapping("/cancel")
    public String cancelPayment(String message) throws JSONException, IOException {
        return paymentCancelService.PaymentCancel(message);
    }

}
