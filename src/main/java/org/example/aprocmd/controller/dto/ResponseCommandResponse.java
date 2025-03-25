package org.example.aprocmd.controller.dto;

import java.time.LocalDateTime;

public record ResponseCommandResponse(String result, LocalDateTime commandTime) {}
