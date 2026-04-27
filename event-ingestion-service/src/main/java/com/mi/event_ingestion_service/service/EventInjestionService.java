package com.mi.event_ingestion_service.service;

import com.mi.event_ingestion_service.models.MediaEventRequest;
import com.mi.event_ingestion_service.repository.MediaInjestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventInjestionService {

    private final MediaInjestionRepository mediaInjestionRepository;

        public List<MediaEventRequest> getAllSavedEvents() {
            return mediaInjestionRepository.findAll();
        }
}
