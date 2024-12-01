package com.quant.craft.backend.presentation.controller;

import com.quant.craft.backend.application.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    @GetMapping("/demo-tradings")
    public SseEmitter demo() {
        return sseService.createEmitter();
    }
}