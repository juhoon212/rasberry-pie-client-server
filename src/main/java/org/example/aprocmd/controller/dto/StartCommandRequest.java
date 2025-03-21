package org.example.aprocmd.controller.dto;

import java.time.LocalDateTime;

record StartCommandRequest(byte[] command, LocalDateTime startRequestTime) {}
