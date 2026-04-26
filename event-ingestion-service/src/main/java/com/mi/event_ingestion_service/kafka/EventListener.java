package com.mi.event_ingestion_service.kafka;

import com.mi.event_ingestion_service.models.MediaEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventListener {

    @KafkaListener(topics = "media-event", groupId = "media-event-group")
    public void handleOrderCreatedEvent(MediaEventRequest event) {
        log.info("media-event received {}", event );
    }
}
