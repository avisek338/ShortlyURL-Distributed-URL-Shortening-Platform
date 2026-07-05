package avisek.example.redirectionservice.service;

import avisek.example.redirectionservice.dto.ClickEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final String TOPIC = "click-events";

    private final KafkaTemplate<String, ClickEvent> kafkaTemplate;

    public void publish(String shortCode, String message) {
        ClickEvent event = ClickEvent.of(shortCode, message);
        log.info("event publisher called");
        CompletableFuture<SendResult<String, ClickEvent>> future =
                kafkaTemplate.send(TOPIC, shortCode, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish ClickEvent [eventId={}]: {}",
                        event.getEventId(), ex.getMessage());
            } else {
                log.info("Published ClickEvent [eventId={}, shortCode={}, partition={}, offset={}]",
                        event.getEventId(),
                        event.getShortCode(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}