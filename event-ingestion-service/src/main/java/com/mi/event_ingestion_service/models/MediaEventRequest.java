package com.mi.event_ingestion_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_event")
public class MediaEventRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_id", nullable = false)
    private String mediaId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "event_timestamp", nullable = false)
    private Instant eventTimestamp;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "playback_position",  nullable = false)
    private Double playbackPosition;

    @Column(name = "device_type")
    private String deviceType;

    private String region;
}
