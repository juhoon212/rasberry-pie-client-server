package org.example.aprocmd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.aprocmd.domain.command.request.CommandType;

import java.time.LocalDateTime;

public record RequestCommandRequest(
        @NotNull CommandType commandType,
        @NotBlank @Size(min = 2, message = "데이터는 2글자 이상이여야 합니다.") String data,
        @NotNull LocalDateTime startRequestTime) {}
