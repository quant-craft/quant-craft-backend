package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.auth.ExchangeApiKey;
import com.quant.craft.backend.domain.order.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeApiKeyRepository extends JpaRepository<ExchangeApiKey, Long> {

  List<ExchangeApiKey> findAllByUserId(Long userId);
}
