package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.strategy.UserStrategy;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.UserRepository;
import com.quant.craft.backend.infrastructure.repository.UserStrategyRepository;
import com.quant.craft.backend.presentation.controller.user.dto.UserResponse;
import com.quant.craft.backend.presentation.controller.user.dto.UserStrategyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository repository;

  private final UserStrategyRepository userStrategyRepository;

  public UserResponse findByUser(Long userId) {
    User user = repository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Not found User. id: " + userId));
    return UserResponse.from(user);
  }

  public UserStrategyResponse findUserStrategies(Long userId) {
    User user = repository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Not found User. id: " + userId));
    List<UserStrategy> userStrategies = userStrategyRepository.findAllByUserId(userId);

    return new UserStrategyResponse(user,
        userStrategies.stream().map(UserStrategy::getStrategy).toList());
  }
}
