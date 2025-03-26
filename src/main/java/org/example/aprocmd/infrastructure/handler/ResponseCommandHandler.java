package org.example.aprocmd.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.*;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.request.ms.RequestMsCommand;
import org.example.aprocmd.domain.command.request.ps.RequestPsCommand;
import org.example.aprocmd.domain.command.request.st.RequestStartCommand;
import org.example.aprocmd.exception.command.CommandNotFoundException;
import org.example.aprocmd.infrastructure.dto.SocketResponseDto;
import org.example.aprocmd.infrastructure.dto.mapper.SocketResponseMapper;
import org.example.aprocmd.util.CommandHelper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseCommandHandler {

    private final CommandHelper commandHelper;
    private final SocketResponseMapper socketResponseMapper;

    public Flux<SocketResponseDto> handleResponse(final Connection conn) {
        return conn
                .inbound()
                .receive()
                .asByteArray()
                .flatMap(response -> Mono.just(socketResponseMapper.mapToSocketResponseDto(response)))
                .doOnNext(response -> log.info("Server response: {}", response.data()));
    }


    public Mono<Command> parseData(final byte[] data) {
        switch (commandHelper.parseBytesToCommandType(data)) {
            case ST:
                return Mono.just(
                        RequestStartCommand.builder()
                                .data(data)
                                .commandType(CommandType.ST)
                                .build());
            case MS:
                return Mono.just(
                        RequestMsCommand.builder()
                                .data(data)
                                .commandType(CommandType.MS)
                                .build());
            case PS:
                return Mono.just(
                        RequestPsCommand.builder()
                                .data(data)
                                .commandType(CommandType.PS)
                                .build());
            default:
                log.error("커맨드가 존재하지 않습니다. 관리자에게 문의하세요 data : {}, {}", data[2], data[3]);
                throw new CommandNotFoundException("커맨드가 존재하지 않습니다. 관리자에게 문의하세요");
        }
    }


}
