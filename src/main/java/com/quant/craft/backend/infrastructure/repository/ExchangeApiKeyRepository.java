package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.auth.ExchangeApiKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeApiKeyRepository extends JpaRepository<ExchangeApiKey, Long> {

  List<ExchangeApiKey> findAllByUserId(Long userId);
}
