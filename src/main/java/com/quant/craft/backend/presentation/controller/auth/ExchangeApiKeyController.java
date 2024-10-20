package com.quant.craft.backend.presentation.controller.auth;

import com.quant.craft.backend.application.service.ExchangeApiKeyService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.auth.dto.request.CreateExchangeApiKeyRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.request.UpdateExchangeApiKeyRequest;
import com.quant.craft.backend.presentation.controller.auth.dto.response.ExchangeApiKeyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exchange-api-keys")
public class ExchangeApiKeyController {

  private final ExchangeApiKeyService service;

  @GetMapping("/list")
  public ResponseEntity<List<ExchangeApiKeyResponse>> list(@RequiredLogin User user) {
    return ResponseEntity.ok(service.findAllByUser(user));
  }

  @GetMapping("/{exchangeApiKeyId}")
  public ExchangeApiKeyResponse show(@RequiredLogin User user, Long exchangeApiKeyId) {
    return service.find(exchangeApiKeyId);
  }

  @PostMapping
  public ResponseEntity<ExchangeApiKeyResponse> create(
      @RequiredLogin User user,
      @RequestBody CreateExchangeApiKeyRequest request
  ) {
    return ResponseEntity.ok(service.create(request, user));
  }

  @PatchMapping("/{exchangeApiKeyId}")
  public ResponseEntity<ExchangeApiKeyResponse> update(
      @RequiredLogin User user,
      @PathVariable Long exchangeApiKeyId,
      @RequestBody UpdateExchangeApiKeyRequest request
  ) {
    return ResponseEntity.ok(service.update(exchangeApiKeyId, request, user));
  }

  @DeleteMapping("/{exchangeApiKeyId}")
  public ResponseEntity<Void> delete(
      @RequiredLogin User user,
      @PathVariable Long exchangeApiKeyId
  ) {
    service.delete(exchangeApiKeyId);
    return ResponseEntity.noContent().build();
  }
}
