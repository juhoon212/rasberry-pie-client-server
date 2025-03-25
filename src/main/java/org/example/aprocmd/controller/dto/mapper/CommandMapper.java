package org.example.aprocmd.controller.dto.mapper;

import org.example.aprocmd.controller.dto.RequestCommandRequest;
import org.example.aprocmd.controller.dto.ResponseCommandResponse;
import org.example.aprocmd.service.dto.RequestCommandDto;
import org.example.aprocmd.service.dto.ResponseCommandDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CommandMapper {
    public RequestCommandDto mapToCommandRequestDto(final RequestCommandRequest commandRequest) {
        return new RequestCommandDto(
                commandRequest.commandType(),
                commandRequest.data(),
                commandRequest.startRequestTime()
        );
    }

    public Mono<ResponseCommandResponse> mapToCommandResponse(final Mono<ResponseCommandDto> commandResponse) {
        return commandResponse.map(responseCommandDto ->
                new ResponseCommandResponse(responseCommandDto.data(), responseCommandDto.responseTime()));
    }
}
