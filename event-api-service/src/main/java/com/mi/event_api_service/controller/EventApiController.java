package com.mi.event_api_service.controller;

import com.mi.event_api_service.kafka.EventProducer;
import com.mi.event_api_service.model.MediaEventRequest;
import com.mi.event_api_service.security.JwtUtils;
import com.mi.event_api_service.service.EventMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventApiController {

    private final EventMediaService eventMediaService;
    private final EventProducer eventProducer;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<String> sentMediaEvent(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenFromFilter, // This token is already stripped of "Bearer " prefix by the AuthenticationFilter
            @Valid @RequestBody MediaEventRequest mediaEventRequest) {

        if (idempotencyKey == null || idempotencyKey.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Idempotency-Key header is required");
        }

        String emailFromToken = jwtUtils.extractEmailFromToken(tokenFromFilter);

        if ( emailFromToken== null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        Boolean isEventNotExists = eventMediaService.sentMediaEvent(mediaEventRequest, idempotencyKey);

        if (isEventNotExists) {
            eventProducer.publishOrderCreatedEvent(mediaEventRequest, emailFromToken);
            return ResponseEntity.status(HttpStatus.CREATED).body("Event received successfully");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Duplicate event detected. This event has already been processed.");
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getAllCachedKeys() {
        Map<String, String> keys = eventMediaService.getAllCachedKeys();
        return ResponseEntity.status(HttpStatus.OK).body(keys);

    }
}
