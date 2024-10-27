package com.quant.craft.backend.infrastructure.repository;

import com.quant.craft.backend.domain.trade.TradingBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingBotRepository extends JpaRepository<TradingBot, Long> {

    List<TradingBot> findAllByUserId(Long userId);
}
