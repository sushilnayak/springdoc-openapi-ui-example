package com.nayak.springdocopenapiuiexample.dto;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
}
