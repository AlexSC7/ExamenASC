package com.exam.asc.infrastructure.adapter.in.http;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
)  {
}
