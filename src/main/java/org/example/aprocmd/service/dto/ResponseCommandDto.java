package org.example.aprocmd.service.dto;

import java.time.LocalDateTime;

public record ResponseCommandDto(String data, LocalDateTime responseTime) {
}
