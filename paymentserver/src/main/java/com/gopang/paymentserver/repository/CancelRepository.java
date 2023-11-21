package com.gopang.paymentserver.repository;

import com.gopang.paymentserver.domain.Cancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelRepository extends JpaRepository<Cancel, Long>, JpaSpecificationExecutor<Cancel> {
}
