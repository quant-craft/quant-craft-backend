package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.payment.PaymentTxn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTxnRepository extends JpaRepository<PaymentTxn, Long> {

    List<PaymentTxn> findAllByUserId(Long userId);
}
