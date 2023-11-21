package com.gopang.itemserver.repository;

import com.gopang.itemserver.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
