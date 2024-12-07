package com.quant.craft.backend.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class SseService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<Long, ScheduledExecutorService> schedulers = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(Long botId) {
        removeEmitter(botId);

        SseEmitter emitter = new SseEmitter(180000L);

        emitter.onCompletion(() -> {
            log.info("SSE connection completed for bot: {}", botId);
            removeEmitter(botId);
        });

        emitter.onTimeout(() -> {
            log.info("SSE connection timeout for bot: {}", botId);
            removeEmitter(botId);
        });

        emitter.onError(ex -> {
            log.error("SSE error occurred for bot: {}", botId, ex);
            removeEmitter(botId);
        });

        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(System.currentTimeMillis()))
                    .name("INIT")
                    .data("connected")
                    .reconnectTime(3000));

            scheduleHeartbeat(botId, emitter);

            emitters.put(botId, emitter);

            log.info("New SSE connection established for bot: {}", botId);
        } catch (IOException e) {
            log.error("Error sending initial message to bot: {}", botId, e);
            removeEmitter(botId);
        }

        return emitter;
    }

    private void scheduleHeartbeat(Long botId, SseEmitter emitter) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setName("sse-heartbeat-" + botId);
            thread.setDaemon(true);
            return thread;
        });

        schedulers.put(botId, scheduler);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!emitters.containsKey(botId)) {
                    log.debug("Emitter not found for bot: {}. Stopping heartbeat.", botId);
                    removeEmitter(botId);
                    return;
                }

                emitter.send(SseEmitter.event()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name("heartbeat")
                        .data("ping")
                        .reconnectTime(3000));

                log.trace("Heartbeat sent to bot: {}", botId);

            } catch (Exception e) {
                log.error("Failed to send heartbeat to bot: {}", botId, e);
                removeEmitter(botId);
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void sendToBot(Long botId, String topic, String message) {
        SseEmitter emitter = emitters.get(botId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name(topic)
                        .data(message)
                        .reconnectTime(3000));

                log.debug("Sent message to bot {}: topic={}, message={}", botId, topic, message);

            } catch (IOException e) {
                log.error("Failed to send message to bot {}: {}", botId, e.getMessage());
                removeEmitter(botId);
            }
        } else {
            log.debug("No emitter found for bot: {}", botId);
        }
    }

    public void broadcast(String topic, String message) {
        for (Map.Entry<Long, SseEmitter> entry : emitters.entrySet()) {
            Long botId = entry.getKey();
            SseEmitter emitter = entry.getValue();

            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name(topic)
                        .data(message)
                        .reconnectTime(3000));

                log.debug("Broadcast market info to bot {}: topic={}, message={}",
                        botId, topic, message);

            } catch (IOException e) {
                log.error("Failed to broadcast market info to bot {}: {}",
                        botId, e.getMessage());
                removeEmitter(botId);
            }
        }
    }

    private void removeEmitter(Long botId) {
        SseEmitter emitter = emitters.remove(botId);
        if (emitter != null) {
            try {
                emitter.complete();
            } catch (Exception e) {
                log.warn("Error completing emitter for bot: {}", botId, e);
            }
        }

        ScheduledExecutorService scheduler = schedulers.remove(botId);
        if (scheduler != null) {
            try {
                scheduler.shutdown();
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
                log.warn("Interrupted while shutting down scheduler for bot: {}", botId, e);
            }
        }

        log.info("Removed SSE connection and scheduler for bot: {}", botId);
    }
}