package org.example.aprocmd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.controller.dto.CommandMapper;
import org.example.aprocmd.controller.dto.RequestCommandRequest;
import org.example.aprocmd.service.RequestCommandService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/request")
@RestController
@RequiredArgsConstructor
public class RequestCommandController {

    private final RequestCommandService requestCommandService;
    private final CommandMapper commandMapper;

    @GetMapping("/command")
    public Mono<?> requestCommand(@RequestBody @Valid final RequestCommandRequest request) {
        return requestCommandService.sendMessage(commandMapper.mapToCommandRequestDto(request));
    }
}
