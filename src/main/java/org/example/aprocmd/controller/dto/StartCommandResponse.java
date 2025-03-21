package org.example.aprocmd.controller.dto;

import java.time.LocalDateTime;

record StartCommandResponse(String result, LocalDateTime commandTime) {}
