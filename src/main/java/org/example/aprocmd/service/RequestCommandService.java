package org.example.aprocmd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandHelper;
import org.example.aprocmd.domain.handler.RequestCommandHandler;
import org.example.aprocmd.exception.CommandNotFoundException;
import org.example.aprocmd.service.dto.RequestCommandDto;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.example.aprocmd.util.CommandUtil.ST_COMMAND_LENGTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCommandService {

    private final CommandHelper commandHelper;

    private final RequestCommandHandler requestCommandHandler;

    public Mono<?> sendMessage(final RequestCommandDto command) {
        if (command == null) {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다. 관리자에게 문의하세요");
        }

        byte[] packet = createPacket(command.command(), command.startTime());
        log.info("Send message hexString: " + ByteUtil.byteArrayToHexString(packet));
        return requestCommandHandler.sendMessage(packet, command.command().getTotalLength());
    }

    public byte[] createPacket(final Command command, final LocalDateTime startTime) {
        switch (command) {
            case MS:
                return command.getCommandByte();
            case ST:
                return createStartCommand(Command.ST, startTime, ST_COMMAND_LENGTH);
            default:
                return null;
        }
    }

    public byte[] createStartCommand(
            final Command command,
            final LocalDateTime startTime,
            final int totalLength
    ) {
        // Command.ST
        byte[] packet = new byte[totalLength];
        commandHelper.createPacketHeader(packet);
        commandHelper.createCommand(packet, command);
        commandHelper.addLength(packet, command);
        packet[6] = (byte) 0x01; // dummy
        packet[7] = (byte) 0x01; // dummy
        commandHelper.addData(packet, startTime);
        commandHelper.addCheckSumAndEtx(packet, command);

        return packet;
    }
}
