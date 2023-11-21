package com.gopang.orderservice.dto;

import com.gopang.orderservice.dto.item.ReqItemOrder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

        public Long user_id;

        public List<ReqItemOrder> ReqItemOrder;

        public String reciever_name;

        public String reciever_phone;

        public String reciever_addr;
}
