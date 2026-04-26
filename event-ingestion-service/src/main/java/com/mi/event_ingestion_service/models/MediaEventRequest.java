package com.mi.event_ingestion_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaEventRequest {
     private String mediaId;

     private String userId;

     private Instant eventTimestamp;

     private EventType eventType;

     private Double playbackPosition;

    // Optional
    private String deviceType;
    // Optional
    private String region;
}
