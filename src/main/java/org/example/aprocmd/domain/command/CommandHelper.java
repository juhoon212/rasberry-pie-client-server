package org.example.aprocmd.domain.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.exception.CommandNotFoundException;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Component;


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

    public void createCommand(final byte[] data, final Command command) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        if (command == null) {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다.");
        }

        data[2] = command.getCommandByte()[0]; // ex) S
        data[3] = command.getCommandByte()[1]; // ex) T
    }

    public void addLength(final byte[] data, final Command command) {
        if (command == null) {
            throw new CommandNotFoundException("커맨드가 존재하지 않습니다.");
        }
        // 2byte
        // 리틀앤디안 방식

        data[LENGTH_POS] = (byte) (command.getCommandLength() & 0xFF);
        data[LENGTH_POS+1] = (byte) ((command.getCommandLength() >> 8) & 0xFF);
    }

    public void addData(final byte[] data, final LocalDateTime startTime) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        byte[] bytes = ByteUtil.localDateTimeToHexString(startTime);
        // data: 공정 시작시간 = index 8 ~ 13
        for (int i = ST_COMMAND_DATA_RANGE[0]; i < ST_COMMAND_DATA_RANGE[1]; ++i) {
            data[i] = bytes[i - ST_COMMAND_DATA_RANGE[0]];
        }
    }

    public void addCheckSumAndEtx(final byte[] data, final Command command) {
        if (data == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        int sum = 0;
        for (int i=0; i<data.length-2; ++i) {
            sum += data[i] & 0xFF;
        }
        data[data.length - 2] = (byte) (sum % 256);
        data[data.length - 1] = command.getEndOfPacket();
    }
}
