package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.point.PointTxn;
import com.quant.craft.backend.domain.point.PointTxnStatus;
import com.quant.craft.backend.domain.strategy.Strategy;
import com.quant.craft.backend.domain.strategy.UserStrategy;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.PointTxnRepository;
import com.quant.craft.backend.infrastructure.repository.StrategyRepository;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.infrastructure.repository.UserStrategyRepository;
import com.quant.craft.backend.presentation.controller.strategy.dto.StrategyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StrategyService {

    private final StrategyRepository repository;

    private final UserRepository userRepository;

    private final UserStrategyRepository userStrategyRepository;

    private final PointTxnRepository pointTxnRepository;

    @Transactional
    public void buyStrategy(User user, Long strategyId) {
        Strategy strategy = repository.findById(strategyId).orElseThrow(() -> new NotFoundException("strategy not found"));

        if (userStrategyRepository.existsByUserIdAndStrategyId(user.getId(), strategyId)) {
            throw new BadRequestException("already bought");
        }

        if (user.getPoint() - strategy.getPrice() < 0) {
            throw new BadRequestException("not enough point");
        }

        user.updatePoint(user.getPoint() - strategy.getPrice());
        userRepository.save(user);

        pointTxnRepository.save(
            PointTxn.builder()
                .user(user)
                .point(strategy.getPrice())
                .status(PointTxnStatus.USE)
                .build()
        );

        userStrategyRepository.save(
            UserStrategy.builder()
                .user(user)
                .strategy(strategy)
                .build()
        );
    }

    public StrategyResponse findStrategy(Long strategyId) {
        Strategy strategy = repository.findById(strategyId).orElseThrow(() -> new NotFoundException("strategy not found"));
        return StrategyResponse.from(strategy);
    }
}
