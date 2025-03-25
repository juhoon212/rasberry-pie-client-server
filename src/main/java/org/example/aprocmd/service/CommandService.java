package org.example.aprocmd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.request.CommandType;
import org.example.aprocmd.infrastructure.handler.ResponseCommandHandler;
import org.example.aprocmd.service.dto.mapper.CommandDtoMapper;
import org.example.aprocmd.util.CommandHelper;
import org.example.aprocmd.infrastructure.handler.RequestCommandHandler;
import org.example.aprocmd.exception.command.CommandNotFoundException;
import org.example.aprocmd.service.dto.RequestCommandDto;
import org.example.aprocmd.service.dto.ResponseCommandDto;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class CommandService {

    private final CommandHelper commandHelper;
    private final RequestCommandHandler requestCommandHandler;
    private final ResponseCommandHandler responseCommandHandler;
    private final CommandDtoMapper commandDtoMapper;

    public Mono<ResponseCommandDto> sendMessage(final RequestCommandDto command) {
        if (command == null || command.commandType() == null || command.startTime() == null) {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다. 관리자에게 문의하세요");
        }

        // 객체 변환 뒤 DB에 저장하는 로직 작성해도 될듯함.
        return createPacket(command.commandType(), command.startTime())
                .doOnNext(bytes -> {
                    log.info("Send message hexString: " + ByteUtil.byteArrayToHexString(bytes));
                })
                .doOnNext(packet ->
                        requestCommandHandler
                                .sendMessage(packet, command.commandType().getRequestTotalLength())
                                .flatMapMany(responseCommandHandler::handleResponse)
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe()
                )
                .flatMap(responseCommandHandler::parseData)
                .flatMap(parsedCommand -> Mono.just(commandDtoMapper.mapToCommandResponseDto(parsedCommand)));
    }

    public Mono<byte[]> createPacket(final CommandType commandType, final LocalDateTime startTime) {
        switch (commandType) {
            case MS:
                return commandHelper.createMsCommand(CommandType.MS, startTime, CommandType.MS.getRequestTotalLength());
            case ST:
                return commandHelper.createStartCommand(CommandType.ST, startTime, CommandType.ST.getRequestTotalLength());
            default:
                return null;
        }
    }
}
