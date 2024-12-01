package com.quant.craft.backend.presentation.controller.trade;

import com.quant.craft.backend.application.service.BackTestingService;
import com.quant.craft.backend.domain.user.User;
import com.quant.craft.backend.presentation.argumentresolver.RequiredLogin;
import com.quant.craft.backend.presentation.controller.trade.dto.request.RunBackTestingRequest;
import com.quant.craft.backend.presentation.controller.trade.dto.response.BackTestingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/backtestings")
public class BackTestingController {

    private final BackTestingService service;

    @PostMapping("/run")
    public BackTestingResponse run(
            @RequiredLogin User user,
            @RequestBody RunBackTestingRequest request
    ) {
        return service.run(user, request);
    }

    @GetMapping("/{backTestingId}")
    public BackTestingResponse find(@PathVariable Long backTestingId) {
        return service.find(backTestingId);
    }
}
