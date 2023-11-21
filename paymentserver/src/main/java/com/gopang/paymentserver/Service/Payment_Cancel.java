package com.gopang.paymentserver.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gopang.paymentserver.authentication.TokenService;
import com.gopang.paymentserver.domain.Cancel;
import com.gopang.paymentserver.dto.Canceldto.CancelStatus;
import com.gopang.paymentserver.repository.CancelRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class Payment_Cancel {

    @Value("${portone.apiBaseUrl}")
    private String API_BASE_URL;
    @Value("${portone.paymentCancel}")
    private String PAYMENT_CANCEL_ENDPOINT;

    @Autowired
    private CancelRepository cancelRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String merchant_uid;
    private int amount;

    @KafkaListener(topics = "paymentCancelRequest-topic", groupId = "payment")
    private void receimessage (String jsonString) throws org.json.JSONException {

        JSONObject jsonBody = new JSONObject(jsonString);

        System.out.println("Received Kafka Message: " + jsonString);

        this.merchant_uid = jsonBody.getString("order_id");
        this.amount = jsonBody.getInt("amount");
    }

    public String PaymentCancel(String message) throws IOException, JSONException {

        try {
            TokenService tokenService = new TokenService();
            String accessToken = tokenService.getToken();

            // RestTemplate 인스턴스 생성
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("merchant_uid", merchant_uid);
            requestBody.put("amount", amount);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            // 요청 바디와 헤더를 이용하여 요청 엔터티 생성
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // API의 URL 정의
            String apiUrl = API_BASE_URL + PAYMENT_CANCEL_ENDPOINT;

            // HTTP POST 요청을 보내고 응답을 받음
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // HTTP 상태 코드 확인
            HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();

                // JSON 응답 파싱
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(responseBody).getAsJsonObject();

                String status = jsonObject.get("response").getAsJsonObject().get("status").getAsString();
                System.out.println("상태: " + status);

                int cancelAmount = jsonObject.get("response").getAsJsonObject().get("cancel_amount").getAsInt();
                int amount1 = jsonObject.get("response").getAsJsonObject().get("amount").getAsInt();
                System.out.println("==========================");
                System.out.println("취소 금액: " + cancelAmount);
                System.out.println("금액: " + amount1);

                Cancel paymentCancel = new Cancel(merchant_uid, cancelAmount, amount1, status);

                System.out.println(paymentCancel);
                cancelRepository.save(paymentCancel);

                if (cancelAmount > amount1) {
                    System.out.println("환불 가능한 금액보다 높은 금액을 입력했습니다.");
                } else {
                    int remainingBalance = amount1 - cancelAmount;
                    System.out.println("남은 잔액: " + remainingBalance);
                    System.out.println("==========================");

                    KafkaPaymentCancel(merchant_uid, cancelAmount,amount1, remainingBalance, status);

                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("cancelAmount", cancelAmount);
                    jsonResponse.put("amount", amount1);
                    jsonResponse.put("remainingBalance", remainingBalance);

                    return jsonResponse.toString();
                }
            } else {
                System.err.println("HTTP 요청이 상태 코드 " + statusCode + "로 실패했습니다.");
                return "환불 가능한 금액을 초과했습니다";
            }
        } catch (Exception e) {
            System.err.println("환불 가능한 금액을 초과했습니다 : Exception e");
            return "환불 가능한 금액을 초과했습니다";
        }
        return message;
    }

    public void KafkaPaymentCancel(String merchant_Uid, int cancelAmount, int amount1, int remainingBalance, String status) throws org.json.JSONException {

        CancelStatus cancelStatus = new CancelStatus(merchant_Uid, cancelAmount, amount1, remainingBalance, status);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("order_id", cancelStatus.merchant_Uid);
        jsonBody.put("cancelAmount", cancelStatus.cancelAmount);
        jsonBody.put("amount", cancelStatus.amount);
        jsonBody.put("remainingBalance", cancelStatus.remainingBalance);
        jsonBody.put("status", cancelStatus.status);

        kafkaTemplate.send("paymentCancelStatus-topic", jsonBody.toString());
    }
}

//        public void KafkaPaymentCancel(String merchant_Uid, int cancelAmount,
//                                          int amount1, int remainingBalance, String status) throws JSONException {
//        // JSON 데이터 to send to Kafka
//        org.springframework.boot.configurationprocessor.json.JSONObject jsonBody = new org.springframework.boot.configurationprocessor.json.JSONObject();
//        jsonBody.put("order_id", merchant_Uid); // 주문번호
//        jsonBody.put("cancelAmount", cancelAmount); // 취소하려는 금액
//        jsonBody.put("amount", amount1); // 총 결제금액
//        jsonBody.put("remainingBalance", remainingBalance); //남은금액
//        jsonBody.put("status", status); // 상태
//
//        // Send JSON data to Kafka topic
//        kafkaTemplate.send("paymentCancelStatus-topic", jsonBody.toString());
//    }
