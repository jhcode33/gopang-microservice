package com.gopang.paymentserver.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gopang.paymentserver.authentication.TokenService;
import com.gopang.paymentserver.domain.Payment;
import com.gopang.paymentserver.repository.PaymentRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class Payment_Detail {

    @Value("${portone.apiBaseUrl}")
    private String API_BASE_URL;
    @Value("${portone.paymentdetail}")
    private String PAYMENT_DETAIL_ENDPOINT;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String merchant_uid;

    @KafkaListener(topics = "paymentRequest-topic", groupId = "payment")
    private void receiveMessage(String jsonString) throws JSONException {
        JSONObject jsonBody = new JSONObject(jsonString);

        System.out.println("Received Kafka Message: " + jsonString);

        if (jsonBody.has("order_id") && !jsonBody.isNull("order_id")) {
            this.merchant_uid = jsonBody.getString("order_id");
        } else {
            // "merchant_uid"가 없거나 null인 경우 처리
            System.err.println("에러: 'order_id'가 없거나 null입니다");
        }
    }

    public String PaymentDetail(String message) throws IOException, JSONException {
        TokenService tokenService = new TokenService();
        OkHttpClient client = new OkHttpClient();

        String accessToken = tokenService.getToken();

        Request request = new Request.Builder()
                .url(API_BASE_URL + PAYMENT_DETAIL_ENDPOINT + "?merchant_uid%5B%5D=" + merchant_uid)
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.code() >= 200 && response.code() < 300) {
                String responseBody = Objects.requireNonNull(response.body()).string();

                JsonParser parser = new JsonParser();
                JsonObject jsonResponse = parser.parse(responseBody).getAsJsonObject();

                JsonArray payments = jsonResponse.getAsJsonArray("response");

                if (payments != null && !payments.isEmpty()) {
                    JsonObject paymentpasing = payments.get(0).getAsJsonObject();

                    String merchantUidpasing = paymentpasing.get("merchant_uid").getAsString();
                    int cancelAmount = paymentpasing.get("cancel_amount").getAsInt();
                    int amount = paymentpasing.get("amount").getAsInt();
                    String cardNumber = paymentpasing.get("card_number").getAsString();
                    String status = paymentpasing.get("status").getAsString();

                    Payment payment = new Payment(merchantUidpasing, amount, status);
                    paymentRepository.save(payment);

                    return "주문번호 : " + merchantUidpasing + "\n"
                            + "환불금액 : " + cancelAmount + "\n"
                            + "결제금액 : " + amount + "\n"
                            + "카드번호 : " + cardNumber + "\n"
                            + "상태 : " + status;  }
            } else {
                System.err.println("오류가 발생했습니다. else 실행"); }
        } return message;
    }

//        public void KafkaPaymentDetail(String merchantUidpasing, int cancelAmount,
//                                          int amount, String cardNumber, String status) throws JSONException {
//        // JSON 데이터 to send to Kafka
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("order_id", merchantUidpasing);
//        jsonBody.put("cancelAmount", cancelAmount);
//        jsonBody.put("amount", amount);
//        jsonBody.put("cardNumber", cardNumber);
//        jsonBody.put("status", status);
//
//        // Send JSON data to Kafka topic
//        kafkaTemplate.send("paymentStatus-topic", jsonBody.toString());
//    }
}


