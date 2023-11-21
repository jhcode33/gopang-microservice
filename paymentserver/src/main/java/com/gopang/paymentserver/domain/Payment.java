package com.gopang.paymentserver.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_ID")
    public Long order_id;
    @Column(nullable = false)
    private String merchant_uid;
    @JoinColumn(name="USER_ID")
    private String user_id;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private String status;
    @CreatedDate
    private LocalDateTime createdAt;

    public Payment(String merchant_uid, int amount, String status) {
        this.merchant_uid = merchant_uid;
        this.amount = amount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

}
