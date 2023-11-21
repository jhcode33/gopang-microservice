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
public class Cancel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_ID")
    public Long order_id;
    @Column(nullable = false)
    private String merchant_uid;
    @Column(nullable = false)
    private int cancel_amount;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private String status;
    @CreatedDate
    private LocalDateTime createdAt;

    public Cancel(String merchant_uid, int cancel_amount, int amount, String status) {
        this.merchant_uid = merchant_uid;
        this.cancel_amount = cancel_amount;
        this.amount = amount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}
