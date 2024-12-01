package com.quant.craft.backend.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SseService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> {
            log.info("SSE connection completed");
            removeEmitter(emitter);
        });

        emitter.onTimeout(() -> {
            log.info("SSE connection timeout");
            removeEmitter(emitter);
        });

        emitter.onError(e -> {
            log.error("SSE error: {}", e.getMessage());
            removeEmitter(emitter);
        });

        emitters.add(emitter);
        return emitter;
    }

    public void broadcast(String topic, String message) {
        List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(topic)
                        .data(message));
            } catch (IOException e) {
                log.error("Error sending SSE: {}", e.getMessage());
                deadEmitters.add(emitter);
            }
        });

        emitters.removeAll(deadEmitters);
    }

    private void removeEmitter(SseEmitter emitter) {
        emitters.remove(emitter);
    }
}
