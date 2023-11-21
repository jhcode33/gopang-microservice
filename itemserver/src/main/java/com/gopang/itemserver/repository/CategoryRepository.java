package com.gopang.itemserver.repository;

import com.gopang.itemserver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c JOIN FETCH c.child WHERE c.parent IS NULL")
    List<Category> findListAll();
}
