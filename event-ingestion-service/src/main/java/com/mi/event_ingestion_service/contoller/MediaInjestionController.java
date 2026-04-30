package com.mi.event_ingestion_service.contoller;

import com.mi.event_ingestion_service.models.MediaEventRequest;
import com.mi.event_ingestion_service.security.JwtUtils;
import com.mi.event_ingestion_service.service.EventInjestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingestion")
public class MediaInjestionController {

    private final EventInjestionService eventInjestionService;
    private final JwtUtils jwtUtils;


    @GetMapping
    public ResponseEntity<?> getAllSavedEvents(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenFromFilter
    ) {

        String emailFromToken = jwtUtils.extractEmailFromToken(tokenFromFilter);

        if ( emailFromToken== null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        List<MediaEventRequest> events = eventInjestionService.getAllSavedEvents(emailFromToken);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

}
