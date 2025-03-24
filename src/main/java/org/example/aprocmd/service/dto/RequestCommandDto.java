package org.example.aprocmd.service.dto;

import org.example.aprocmd.domain.command.Command;

import java.time.LocalDateTime;

public record RequestCommandDto(Command command, LocalDateTime startTime) {
}
