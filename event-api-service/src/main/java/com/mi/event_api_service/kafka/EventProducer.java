package com.mi.event_api_service.kafka;

import com.mi.event_api_service.model.MediaEventRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProducer {
    private static final String TOPIC = "media-event";
    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);
    private final KafkaTemplate<String, MediaEventRequest> kafkaTemplate;

    public void publishOrderCreatedEvent(MediaEventRequest mediaEventRequest) {
        kafkaTemplate.send(TOPIC, mediaEventRequest.getMediaId(), mediaEventRequest)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent successfully to partition: {}, offset: {}"
                                , result.getRecordMetadata().partition()
                                , result.getRecordMetadata().offset());
                    } else {
                        log.info("Failed to send message: {}", ex.getMessage());
                    }
                });
    }
}
