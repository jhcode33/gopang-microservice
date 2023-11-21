### Docker_Build

- docker build -t gopangpayment:0.0.1 .
---
### docker-compose 실행

- docker-compose up
---
### Kafka_Topic

- 카프카로 받고 보내는 형식은 모두 JSON <br><br>
  - Payment_start : payment
  - Payment_Detail : payment_detail
  - Payment_Cancel : payment_cancel
---

- 카프카로 정보를 받아와서 가지고 있다가 포스트맨에 
  입력시에 해당 로직을 수행함. <br><br>

  - POST || /api/v1/payment/pay 
  - GET || /api/v1/payment/detail 
  - POST || /api/v1/payment/cancel
---

{
"merchant_uid": "{{}}}",
"amount": 100,
"card_info": {
"number": "5465-9699-1234-5678",
"expiry": "2027-05",
"birth": "940123",
"pwd_2digit": "12",
"cvc": "123"
},
"cancel_amount": 100
}
