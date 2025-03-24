package org.example.aprocmd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandHelper;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.example.aprocmd.util.CommandUtil.ST_COMMAND_LENGTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCommandService {

    private final CommandHelper commandHelper;
    public byte[] createPacket(final Command command, final LocalDateTime startTime) {
        switch (command) {
            case MS:
                return command.getCommandByte();
            case ST:
                createStartCommand(Command.ST, startTime, ST_COMMAND_LENGTH);
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
        packet[6] = 0x01;
        packet[7] = 0x01;
        commandHelper.addData(packet, startTime);
        commandHelper.addCheckSumAndEtx(packet, command);

        return packet;
    }
}
