package com.quant.craft.backend.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SseService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(Long botId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> {
            log.info("SSE connection completed for bot: {}", botId);
            removeEmitter(botId);
        });

        emitter.onTimeout(() -> {
            log.info("SSE connection timeout for bot: {}", botId);
            removeEmitter(botId);
        });

        emitter.onError(e -> {
            log.error("SSE error for bot {}: {}", botId, e.getMessage());
            removeEmitter(botId);
        });

        emitters.put(botId, emitter);
        return emitter;
    }


    public void sendToBot(Long botId, String topic, String message) {
        SseEmitter emitter = emitters.get(botId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(topic)
                        .data(message));
            } catch (IOException e) {
                log.error("Error sending SSE to bot {}: {}", botId, e.getMessage());
                removeEmitter(botId);
            }
        }
    }

    private void removeEmitter(Long botId) {
        emitters.remove(botId);
    }
}
