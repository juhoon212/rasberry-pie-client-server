package org.example.aprocmd.domain.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.util.ByteUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.example.aprocmd.util.CommandUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandHelper {

    public byte[] createPacket(final Command command, final LocalDateTime startTime) {
        switch (command.getType()) {
            case "MS":
                return command.getCommandByte();
            case "ST":
                createStartCommand(command, startTime);
            default:
                return null;
        }
    }

    public void createPacketHeader(final byte[] byteArray) {
        for (int i=0; i<2; ++i) {
            byteArray[i] = DEFAULT_BLOCK[i];
        }
    }

    public void createCommand(final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("데이터가 잘못되었습니다. 관리자에게 문의하세요");
        }
        data[2] = Command.ST.getCommandByte()[0]; // S
        data[3] = Command.ST.getCommandByte()[1]; // T
    }

    public void createLength(final Command command, final byte[] data) {
        // 2byte
        // 리틀앤디안 방식
        data[LENGTH_POS] = (byte) (command.getDataLength() & 0xFF);
        data[LENGTH_POS+1] = (byte) ((command.getDataLength() >> 8) & 0xFF);
    }

    public void createCheckSumAndEtx(final Command command, final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("데이터가 잘못되었습니다. 관리자에게 문의하세요");
        }

        int sum = 0;
        for (int i=0; i<data.length-2; ++i) {
            sum += data[i] & 0xFF;
        }
        data[data.length - 2] = (byte) (sum & 0xFF);
        data[data.length - 1] = command.getEndOfPacket();
    }

    public byte[] createStartCommand(final Command command, final LocalDateTime startTime) {
        // Command.ST
        byte[] packet = new byte[ST_COMMAND_LENGTH];
        createPacketHeader(packet);
        createLength(command, packet);
        createCheckSumAndEtx(command, packet);

        return ByteUtil.localDateTimeToByteArray(startTime);
    }
}
