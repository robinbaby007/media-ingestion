package com.mi.event_ingestion_service.kafka;

import com.mi.event_ingestion_service.models.MediaEventRequest;
import com.mi.event_ingestion_service.repository.MediaInjestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventListener {

    private final MediaInjestionRepository mediaInjestionRepository;

    @KafkaListener(topics = "media-event", groupId = "media-event-group")
    public void handleOrderCreatedEvent(MediaEventRequest event) {
        mediaInjestionRepository.save(event);
        log.info("media-event received {}", event );
    }
}
