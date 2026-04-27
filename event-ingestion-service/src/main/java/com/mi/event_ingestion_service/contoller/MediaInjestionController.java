package com.mi.event_ingestion_service.contoller;

import com.mi.event_ingestion_service.models.MediaEventRequest;
import com.mi.event_ingestion_service.service.EventInjestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingestion")
public class MediaInjestionController {

    private final EventInjestionService eventInjestionService;

    @GetMapping
    public ResponseEntity<List<MediaEventRequest>> getAllSavedEvents() {
        List<MediaEventRequest> events = eventInjestionService.getAllSavedEvents();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

}
