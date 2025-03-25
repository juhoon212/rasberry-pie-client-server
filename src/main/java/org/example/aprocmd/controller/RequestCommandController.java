package org.example.aprocmd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.controller.dto.CommandMapper;
import org.example.aprocmd.controller.dto.RequestCommandRequest;
import org.example.aprocmd.controller.dto.ResponseCommandResponse;
import org.example.aprocmd.domain.command.request.CommandType;
import org.example.aprocmd.service.CommandService;
import org.example.aprocmd.service.dto.RequestCommandDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/request")
@RestController
@RequiredArgsConstructor
public class RequestCommandController {

    private final CommandService commandService;
    private final CommandMapper commandMapper;

    @GetMapping("/command/start") // ST
    public Mono<ResponseCommandResponse> startRequestCommand(@RequestBody @Valid final RequestCommandRequest request) {

        if (request.commandType() != CommandType.ST) {
            throw new IllegalArgumentException("커맨드 타입을 확인하시기 바랍니다");
        }
        RequestCommandDto requestCommandDto = commandMapper.mapToCommandRequestDto(request);
        return commandMapper.mapToCommandResponse(commandService.sendMessage(requestCommandDto));
    }

    @GetMapping("/command/status") // MS
    public Mono<ResponseCommandResponse> requestCommand(@RequestBody @Valid final RequestCommandRequest request) {

        if (request.commandType() != CommandType.MS) {
            throw new IllegalArgumentException("커맨드 타입을 확인하시기 바랍니다");
        }
        RequestCommandDto requestCommandDto = commandMapper.mapToCommandRequestDto(request);
        return commandMapper.mapToCommandResponse(commandService.sendMessage(requestCommandDto));
    }
}
