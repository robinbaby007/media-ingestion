package com.mi.event_api_service.utils;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    @Builder.Default()
    private LocalDateTime timestamp = LocalDateTime.now();
}
