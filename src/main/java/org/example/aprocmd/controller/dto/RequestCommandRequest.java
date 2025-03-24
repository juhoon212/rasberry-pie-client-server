package org.example.aprocmd.controller.dto;

import jakarta.validation.constraints.NotNull;
import org.example.aprocmd.domain.command.Command;

import java.time.LocalDateTime;

public record RequestCommandRequest(@NotNull Command command, @NotNull LocalDateTime startRequestTime) {}
