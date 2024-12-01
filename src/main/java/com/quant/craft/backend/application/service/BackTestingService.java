package com.quant.craft.backend.application.service;

import com.quant.craft.backend.domain.trade.BackTesting;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.exception.NotFoundException;
import com.quant.craft.backend.infrastructure.client.trade.BackTestingClient;
import com.quant.craft.backend.infrastructure.repository.BackTestingRepository;
import com.quant.craft.backend.presentation.controller.trade.dto.request.RunBackTestingRequest;
import com.quant.craft.backend.presentation.controller.trade.dto.response.BackTestingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BackTestingService {

    private final BackTestingClient client;

    private final BackTestingRepository repository;

    public BackTestingResponse run(User user, RunBackTestingRequest request) {
        BackTesting backTesting = client.run(
                new com.quant.craft.backend.infrastructure.client.trade.dto.RunBackTestingRequest(
                        user.getId(),
                        request.getStartDate(),
                        request.getEndDate(),
                        request.getStrategyId(),
                        request.getCash(),
                        request.getCommission()
                )
        );
        return BackTestingResponse.from(backTesting);
    }

    public BackTestingResponse find(Long backTestingId) {
        BackTesting backTesting = repository.findById(backTestingId)
                .orElseThrow(() -> new NotFoundException("BackTesting not found"));
        return BackTestingResponse.from(backTesting);
    }
}
