package com.gopang.itemserver.repository;

import com.gopang.itemserver.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
}
