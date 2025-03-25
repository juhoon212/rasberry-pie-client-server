package org.example.aprocmd.service.dto;

import org.example.aprocmd.domain.command.request.CommandType;

import java.time.LocalDateTime;

public record RequestCommandDto(CommandType commandType, String data, LocalDateTime startTime) {
}
