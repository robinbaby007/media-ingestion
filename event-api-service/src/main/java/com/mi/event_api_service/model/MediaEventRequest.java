package com.mi.event_api_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaEventRequest {
    @NotBlank(message = "Media ID is required")
    private String mediaId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Event timestamp is required")
    @PastOrPresent(message = "Timestamp cannot be in the future")
    private Instant eventTimestamp;

    @NotNull(message = "Event type is required")
    private EventType eventType;

    @NotNull(message = "Playback position is required")
    @PositiveOrZero(message = "Playback position must be 0 or greater")
    private Double playbackPosition;

    // Optional
    private String deviceType;
    // Optional
    private String region;
}
