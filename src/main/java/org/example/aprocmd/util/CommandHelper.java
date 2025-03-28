package org.example.aprocmd.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandType;
import org.example.aprocmd.domain.command.request.ms.RequestMsCommand;
import org.example.aprocmd.domain.command.request.pc.RequestPcCommand;
import org.example.aprocmd.domain.command.request.st.RequestStartCommand;
import org.example.aprocmd.exception.command.CommandNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;

import static org.example.aprocmd.util.CommandUtil.*;
import static org.example.aprocmd.util.ExceptionUtil.ILLEGAL_ARGUMENT_DEFAULT_MESSAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandHelper {

    public void createPacketHeader(final byte[] byteArray) {
        if (byteArray == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        for (int i=0; i<2; ++i) {
            byteArray[i] = DEFAULT_PACKET_HEADER[i];
        }
    }

    public void createCommand(final byte[] data, final CommandType commandType) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        if (commandType == null) {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다.");
        }

        data[2] = commandType.getCommandByte()[0]; // ex) S
        data[3] = commandType.getCommandByte()[1]; // ex) T

    }

    public byte[] addLength(final byte[] data, final CommandType commandType) {
        if (commandType == null) {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다.");
        }
        // 2byte
        // 리틀앤디안 방식
        data[LENGTH_POS] = (byte) (commandType.getDataAreaLength() & 0xFF); // 5번째
        data[LENGTH_POS+1] = (byte) ((commandType.getDataAreaLength() >> 8) & 0xFF); // 6번째

        return data;
    }

    public void addData(final byte[] data, final LocalDateTime startTime, int[] dataRange) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        byte[] bytes = ByteUtil.localDateTimeToHexString(startTime);
        for (int i=0; i<bytes.length; ++i) {
            data[dataRange[0] + i] = bytes[i];
        }
    }

    public void addCheckSumAndEtx(final byte[] data, final CommandType commandType) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        int sum = 0;
        for (int i = 0; i < data.length - 2; ++i) {
            sum += data[i] & 0xFF;
        }
        data[data.length - 2] = (byte) (sum % 256);
        data[data.length - 1] = commandType.getEndOfPacket();
    }

    public CommandType parseBytesToCommandType(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        byte mainCommand = data[2];
        byte subCommand = data[3];

        if (mainCommand == CommandType.ST.getCommandByte()[0] && subCommand == CommandType.ST.getCommandByte()[1]) {
            return CommandType.ST;
        } else if (mainCommand == CommandType.MS.getCommandByte()[0] && subCommand == CommandType.MS.getCommandByte()[1]) {
            return CommandType.MS;
        } else if (mainCommand == CommandType.PS.getCommandByte()[0] && subCommand == CommandType.PS.getCommandByte()[1]) {
            return CommandType.PS;
        } else {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다.");
        }
    }

    public Mono<? extends Command> parseBytesToCommand(final byte[] data, LocalDateTime startCommandTime) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        byte mainCommand = data[2];
        byte subCommand = data[3];

        if (mainCommand == CommandType.ST.getCommandByte()[0] && subCommand == CommandType.ST.getCommandByte()[1]) {
            return Mono.just(
                    RequestStartCommand.builder()
                            .data(data)
                            .commandType(CommandType.ST)
                            .startCommandTime(startCommandTime)
                            .build()
            );
        } else if (mainCommand == CommandType.MS.getCommandByte()[0] && subCommand == CommandType.MS.getCommandByte()[1]) {
            return Mono.just(
                    RequestMsCommand.builder()
                            .data(data)
                            .commandType(CommandType.MS)
                            .build()
            );
        } else if (mainCommand == CommandType.PS.getCommandByte()[0] && subCommand == CommandType.PS.getCommandByte()[1]) {
            return Mono.just(
                    RequestPcCommand.builder()
                            .data(data)
                            .commandType(CommandType.PS)
                            .build()
            );
        } else {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다.");
        }
    }

    public Mono<byte[]> createStartCommand(
            final CommandType commandType,
            final LocalDateTime startTime,
            final int totalLength
    ) {
        // CommandType.ST
        byte[] packet = new byte[totalLength];
        createPacketHeader(packet);
        createCommand(packet, commandType);
        addLength(packet, commandType);
        packet[6] = (byte) 0x01; // dummy
        packet[7] = (byte) 0x01; // dummy
        addData(packet, startTime, ST_COMMAND_DATA_RANGE);
        addCheckSumAndEtx(packet, commandType);

        return Mono.just(packet);
    }

    public Mono<byte[]> createMsCommand(
            final CommandType commandType,
            final LocalDateTime startTime,
            final int totalLength
    ) {
        // CommandType.ST
        byte[] packet = new byte[totalLength];
        createPacketHeader(packet);
        createCommand(packet, commandType);
        addLength(packet, commandType);
        packet[6] = (byte) 0x44; // Date&Time flag -> 문서 참고
        // 1980년 기준이기 때문에 20년을 더해준다.
        if (commandType == CommandType.MS) {
            LocalDateTime plusYearStartTime = LocalDateTime.of(
                    startTime.getYear() + 20, startTime.getMonth(),
                    startTime.getDayOfMonth(),
                    startTime.getHour(),
                    startTime.getMinute(),
                    startTime.getSecond()
            );
            addData(packet, plusYearStartTime, MS_COMMAND_DATA_RANGE);
        } else {
            addData(packet, startTime, MS_COMMAND_DATA_RANGE);
        }
        addCheckSumAndEtx(packet, commandType);

        return Mono.just(packet);
    }


    /*public Mono<byte[]> createPsCommand(
            final CommandType commandType,
            final LocalDateTime startTime,
            final int totalLength
    ) {
        // CommandType.ST
        byte[] packet = new byte[totalLength];
        createPacketHeader(packet);
        createCommand(packet, commandType);
        addLength(packet, commandType);
        packet[6] = (byte) 0x01; // dummy
        packet[7] = (byte) 0x01; // dummy
        addData(packet, startTime, PS_COMMAND_DATA_RANGE);
        addCheckSumAndEtx(packet, commandType);

        return Mono.just(packet);
    }
     */
}
