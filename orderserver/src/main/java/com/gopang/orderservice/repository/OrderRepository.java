package com.gopang.orderservice.repository;

import com.gopang.orderservice.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
