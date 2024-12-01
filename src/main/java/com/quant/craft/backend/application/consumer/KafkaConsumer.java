package com.quant.craft.backend.application.consumer;

import com.quant.craft.backend.application.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SseService sseService;

    @KafkaListener(
            topics = "market.info",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMarketInfo(
            String message,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        try {
            log.info("Received market info - topic: {}, timestamp: {}, message: {}",
                    topic, Instant.ofEpochMilli(timestamp), message);

            sseService.broadcast(topic, message);
        } catch (Exception e) {
            log.error("Error processing market info message: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(
            topics = "trading.events",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTradingEvents(
            String message,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        try {
            log.info("Received trading event - topic: {}, timestamp: {}, message: {}",
                    topic, Instant.ofEpochMilli(timestamp), message);

            sseService.broadcast(topic, message);
        } catch (Exception e) {
            log.error("Error processing trading event message: {}", e.getMessage(), e);
        }
    }
}
