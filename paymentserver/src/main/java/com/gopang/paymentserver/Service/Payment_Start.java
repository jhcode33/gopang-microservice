package com.gopang.paymentserver.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopang.paymentserver.authentication.TokenService;
import com.gopang.paymentserver.domain.Card;
import com.gopang.paymentserver.dto.Paymentdto.PaymentStatus;
import com.gopang.paymentserver.dto.Paymentdto.StatusUpdate;
import com.gopang.paymentserver.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class Payment_Start {

    @Value("${portone.apiBaseUrl}")
    private String API_BASE_URL;
    @Value("${portone.paymentstart}")
    private String PAYMENT_ENDPOINT;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String Pay_merchant_uid;
    private int Pay_amount;
    private String Pay_card_number = "5465-9699-1234-5678";
    private String Pay_expiry = "2027-05";
    private String Pay_birth = "940123";
    private String Pay_pwd_2digit = "12";
    private String Pay_cvc = "123";

    @KafkaListener(topics = "paymentRequest-topic", groupId = "payment")
    private void KafkaLisPayment(String jsonString) throws JSONException {

            JSONObject jsonBody = new JSONObject(jsonString);
            System.out.println("Received Kafka Message: " + jsonString);

            Pay_merchant_uid = jsonBody.optString("order_id", null);
            Pay_amount = jsonBody.optInt("amount", 0);
            Pay_card_number = jsonBody.optString("card_number", null);
            Pay_expiry = jsonBody.optString("expiry", null);
            Pay_birth = jsonBody.optString("birth", null);
            Pay_pwd_2digit = jsonBody.optString("pwd_2digit", null);
            Pay_cvc = jsonBody.optString("cvc", null);
    }

    public String Payment(String message) throws IOException, JSONException{

            TokenService tokenService = new TokenService();
            String accessToken = tokenService.getToken();

            RestTemplate restTemplate = new RestTemplate();

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("merchant_uid", Pay_merchant_uid);
            jsonBody.put("amount", Pay_amount);
            jsonBody.put("card_number", Pay_card_number);
            jsonBody.put("expiry", Pay_expiry);
            jsonBody.put("birth", Pay_birth);
            jsonBody.put("pwd_2digit", Pay_pwd_2digit);
            jsonBody.put("cvc", Pay_cvc);

            // JSON 데이터와 적절한 미디어 타입을 설정하여 HttpEntity를 만듭니다.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody.toString(), headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_BASE_URL + PAYMENT_ENDPOINT, requestEntity, String.class);

            int responseCode = responseEntity.getStatusCodeValue(); // HTTP 응답 코드 얻기
            System.out.println("Response: " + responseEntity.getBody());

        if (responseCode == 200) {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

            // 파싱할 데이터 추출
            String Pay_merchant_uid = responseJson.path("response").path("merchant_uid").asText();
            String paymentStatus = responseJson.path("response").path("status").asText();

            Card CardEntity = new Card(Pay_merchant_uid, Pay_card_number, Pay_expiry, Pay_birth, Pay_pwd_2digit, Pay_cvc, LocalDateTime.now());
            cardRepository.save(CardEntity);

            PaymentStatus successStatus = PaymentStatus.PAYCOMPLETE;
            KafkaPayment(Pay_merchant_uid, successStatus);

            return "merchantUid = " + Pay_merchant_uid + "\n"
                    + "amount = " + Pay_amount + "\n"
                    + "status = " + paymentStatus;
        } else {
            PaymentStatus failureStatus = PaymentStatus.PAYFAIL;
            KafkaPayment(Pay_merchant_uid, failureStatus);
            return "결제실패";
        }
    }

    public void KafkaPayment(String Pay_merchant_uid, PaymentStatus paymentStatus) {
        StatusUpdate statusupdate = new StatusUpdate();
        statusupdate.orderId = Pay_merchant_uid;
        statusupdate.paymentStatus = paymentStatus;

        try {
            String statusUpdateJson = objectMapper.writeValueAsString(statusupdate);
            kafkaTemplate.send("paymentStatus-topic", statusUpdateJson);
        } catch (Exception e) {
            System.out.println("KafkaPaymentException");
        }
    }
}
//    public void KafkaPayment(String merchantUid, String status) throws JSONException {
//
//        JSONObject jsonBody = new JSONObject();
//
//        jsonBody.put("order_id", merchantUid);
//        jsonBody.put("status", status);
//
//        kafkaTemplate.send("paymentStatus-topic", jsonBody.toString());
//    }
