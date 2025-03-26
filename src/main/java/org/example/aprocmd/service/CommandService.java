package org.example.aprocmd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandType;
import org.example.aprocmd.domain.command.request.st.RequestStartCommand;
import org.example.aprocmd.exception.command.CreateDtoException;
import org.example.aprocmd.exception.command.RequestStartCommandCreateException;
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
                .flatMap(bytes -> commandHelper.parseBytesToCommand(bytes, command.startTime()))
                .switchIfEmpty(Mono.error(new RequestStartCommandCreateException("start 명령을 전송할 수 없습니다.")))
                .doOnNext(requestCommand ->
                        requestCommandHandler
                                .sendMessage(requestCommand.getCommand(), command.commandType().getTotalLength())
                                .flatMapMany(responseCommandHandler::handleResponse)
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe()
                )
                .doOnSuccess(parsedData ->
                        log.info("Parsed response: hex data: {}, commandType: {}",
                                ByteUtil.byteArrayToHexString(parsedData.getData()),
                                parsedData.getCommandType().getType()
                        )
                )
                .flatMap(parsedCommand -> Mono.just(commandDtoMapper.mapToCommandResponseDto(parsedCommand)))
                .switchIfEmpty(Mono.error(new CreateDtoException("응답 생성에 문제가 있습니다. 관리자에게 문의하세요.")));
    }

    public Mono<byte[]> createPacket(final CommandType commandType, final LocalDateTime startTime) {
        switch (commandType) {
            case MS:
                return commandHelper.createMsCommand(CommandType.MS, startTime, CommandType.MS.getTotalLength());
            case ST:
                return commandHelper.createStartCommand(CommandType.ST, startTime, CommandType.ST.getTotalLength());
            case PS:
                return commandHelper.createPsCommand(CommandType.PS, startTime, CommandType.PS.getTotalLength());
            default:
                return null;
        }
    }
}
