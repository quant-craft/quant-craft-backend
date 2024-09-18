package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.payment.PaymentTxn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTxnRepository extends JpaRepository<PaymentTxn, Long> {
}
