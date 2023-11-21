package com.gopang.orderservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE orders SET delete_yn='Y' WHERE order_id=? ")
public class Orders {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    public Long user_id;

    // 총 주문금액
    public Long amount;

    @Builder.Default
    @OneToMany(mappedBy = "orders")
    public List<OrderDetails> orderHistory = new ArrayList<>();

    public String reciever_name;

    public String reciever_phone;

    public String reciever_addr;

    public String deleteYn;

    public LocalDateTime time;

    // 주문 상태
    @Column
    @Enumerated(EnumType.STRING)
    public OrderState order_state;

}
