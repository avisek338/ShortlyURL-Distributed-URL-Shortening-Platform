package avisek.example.analyticsservice.service;

import avisek.example.analyticsservice.dto.ClickEvent;
import avisek.example.analyticsservice.entity.ClickLog;
import avisek.example.analyticsservice.repository.ClickLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClickEventConsumer {
    private final ClickLogRepository clickLogRepository;
    @KafkaListener(
            topics = "click-events",
            groupId = "click-event-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(List<ConsumerRecord<String, ClickEvent>> records){

        List<ClickLog>clickEvents  = new ArrayList<>();

        records.forEach(record -> {
            ClickLog clickLog = new ClickLog();
            ClickEvent clickEvent = record.value();


            clickLog.setEventId(UUID.fromString(clickEvent.getEventId()));
            clickLog.setShortUrlCode(clickEvent.getShortCode());
            clickLog.setClickedAt(clickEvent.getTimestamp());

            clickEvents.add(clickLog);

            log.info("Consumed [eventId={} partition={}, offset={}]",
                    clickEvent.getEventId(),record.partition(),record.offset());
            });

        clickLogRepository.bulkInsert(clickEvents);

        log.info("batch processing completed");
    }
}
