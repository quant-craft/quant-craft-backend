package com.quant.craft.backend.application.consumer;

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

            // TODO: 실시간 시세 데이터 처리 로직 구현
            // 예: JSON 파싱, 데이터베이스 저장, 웹소켓으로 클라이언트에 전달 등

        } catch (Exception e) {
            log.error("Error processing market info message: {}", e.getMessage(), e);
        }
    }

    // 거래 정보 처리
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

            // TODO: 거래 정보 처리 로직 구현
            // 예: 포지션 업데이트, 거래 내역 저장 등

        } catch (Exception e) {
            log.error("Error processing trading event message: {}", e.getMessage(), e);
        }
    }
}
