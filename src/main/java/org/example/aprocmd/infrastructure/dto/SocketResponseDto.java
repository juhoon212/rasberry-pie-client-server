package org.example.aprocmd.infrastructure.dto;

import java.time.LocalDateTime;

public record SocketResponseDto(String data, LocalDateTime responseTime) {}
