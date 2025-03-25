package org.example.aprocmd.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.response.Command;
import org.example.aprocmd.domain.command.request.CommandType;
import org.example.aprocmd.domain.command.response.MsCommand;
import org.example.aprocmd.domain.command.response.StartCommand;
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


    public Mono<Command> parseData(byte[] data) {
        switch (commandHelper.parseCommand(data)) {
            case ST:
                StartCommand startCommand = new StartCommand(data, CommandType.ST, data);
                return Mono.just(startCommand);
            case MS:
                MsCommand msCommand = new MsCommand(data, CommandType.MS, data);
                return Mono.just(msCommand);
            default:
                log.error("커맨드가 존재하지 않습니다. 관리자에게 문의하세요 data : {}, {}", data[2], data[3]);
                throw new CommandNotFoundException("커맨드가 존재하지 않습니다. 관리자에게 문의하세요");
        }
    }


}
