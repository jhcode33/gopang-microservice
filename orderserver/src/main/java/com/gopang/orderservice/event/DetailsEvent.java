package com.gopang.orderservice.event;

import com.gopang.orderservice.dto.OrderRequest;
import com.gopang.orderservice.dto.item.ResItemOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsEvent {
    public String message;

    public Long orders_id;

    public OrderRequest orderRequest;

    public List<ResItemOrder> resItemOrder;
}
