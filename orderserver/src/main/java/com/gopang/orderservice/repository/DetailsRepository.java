package com.gopang.orderservice.repository;

import com.gopang.orderservice.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailsRepository extends JpaRepository<OrderDetails, Long> {
    @Query("SELECT h FROM OrderDetails h WHERE h.user_id=:userId")
    List<OrderDetails> findAllByUserId(@Param("userId") Long userId);

}
