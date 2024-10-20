package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.auth.ExchangeApiKey;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.BadRequestException;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.repository.ExchangeApiKeyRepository;
import com.quant.craft.backend.presentation.controller.auth.dto.request.CreateExchangeApiKeyRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.request.UpdateExchangeApiKeyRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.response.ExchangeApiKeyResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExchangeApiKeyService {

  private final ExchangeApiKeyRepository repository;

  public List<ExchangeApiKeyResponse> findAllByUser(User user) {
    return repository.findAllByUserId(user.getId()).stream()
        .map(ExchangeApiKeyResponse::from)
        .toList();
  }

  public ExchangeApiKeyResponse find(Long exchangeApiKeyId) {
    ExchangeApiKey exchangeApiKey = repository.findById(exchangeApiKeyId)
        .orElseThrow(() -> new NotFoundException("ExchangeApiKey not found"));
    return ExchangeApiKeyResponse.from(exchangeApiKey);
  }

  @Transactional
  public ExchangeApiKeyResponse create(CreateExchangeApiKeyRequest request, User user) {
    ExchangeApiKey exchangeApiKey = repository.save(ExchangeApiKey.builder()
        .exchange(request.getExchange())
        .apiKey(request.getApiKey())
        .secretKey(request.getSecretKey())
        .user(user)
        .build());
    return ExchangeApiKeyResponse.from(exchangeApiKey);
  }

  @Transactional
  public ExchangeApiKeyResponse update(
      Long exchangeApiKeyId,
      UpdateExchangeApiKeyRequest request,
      User user
  ) {
    ExchangeApiKey oldbie = repository.findById(exchangeApiKeyId)
        .orElseThrow(() -> new NotFoundException("ExchangeApiKey not found"));

    if (!oldbie.getUser().getId().equals(user.getId())) {
      throw new BadRequestException("Only the owner can update the ExchangeApiKey");
    }

    ExchangeApiKey newbie = ExchangeApiKey.builder()
        .id(oldbie.getId())
        .exchange(request.getExchange())
        .apiKey(request.getApiKey())
        .secretKey(request.getSecretKey())
        .user(oldbie.getUser())
        .build();

    return ExchangeApiKeyResponse.from(repository.save(newbie));
  }

  @Transactional
  public void delete(Long exchangeApiKeyId) {
    repository.deleteById(exchangeApiKeyId);
  }
}
