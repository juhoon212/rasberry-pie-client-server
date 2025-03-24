package org.example.aprocmd.controller.dto;

import org.example.aprocmd.service.dto.RequestCommandDto;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper {
    public RequestCommandDto mapToCommandRequestDto(final RequestCommandRequest commandRequest) {
        return new RequestCommandDto(commandRequest.command(), commandRequest.startRequestTime());
    }
}
