package org.example.aprocmd.controller.dto;

import java.time.LocalDateTime;

public record CommandResponse(String result, LocalDateTime commandTime) {}
