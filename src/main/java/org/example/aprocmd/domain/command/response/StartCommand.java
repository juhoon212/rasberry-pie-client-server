package org.example.aprocmd.domain.command.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.request.CommandType;

import static org.example.aprocmd.util.CommandUtil.ST_COMMAND_DATA_RANGE;
import static org.example.aprocmd.util.ExceptionUtil.*;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class StartCommand implements Command {

    private CommandType commandType;
    private byte[] header = new byte[2];

    private byte mainCommand;
    private byte subCommand;
    private byte[] temp = new byte[]{0x01, 0x01};
    private byte[] length = new byte[2];
    private byte[] data = new byte[6];

    private byte checkSum;
    private byte etx;

    private byte[] totalData;

    @Builder
    public StartCommand(byte[] data, CommandType commandType, byte[] totalData) {
        this.validate(data);

        this.commandType = commandType;
        this.setHeader(data);
        this.setMainCommand(data, commandType);
        this.setSubCommand(data, commandType);
        this.setData(data);
        this.setLength(data);
        this.totalData = totalData;
    }

    @Override
    public void validate(byte[] data) {
        final byte nullData = 0x00;

        if (data == null) {
            log.error("StartCommand validate() : 데이터가 존재하지 않습니다.");
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        if (data[0] == nullData || data[1] == nullData) {
            log.error("StartCommand validate() : 헤더가 존재하지 않습니다.");
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        if (data.length > 16) {
            log.error("StartCommand validate() : 데이터 길이가 16보다 큽니다.");
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }
    }

    @Override
    public void setHeader(byte[] data) {
        this.header[0] = data[0];
        this.header[1] = data[1];
    }

    @Override
    public void setMainCommand(byte[] data, CommandType commandType) {
        if (commandType == CommandType.ST) {
            this.mainCommand = data[2];
        }

    }

    @Override
    public void setSubCommand(byte[] data, CommandType commandType) {
        if (commandType == CommandType.ST) {
            this.subCommand = data[3];
        }
    }

    @Override
    public void setLength(byte[] data) {
        this.length[0] = data[4];
        this.length[1] = data[5];
    }

    @Override
    public void setData(byte[] requestData) {
        for (int i=ST_COMMAND_DATA_RANGE[0]; i<requestData.length-2; ++i) {
            data[i - ST_COMMAND_DATA_RANGE[0]] = requestData[i];
        }
    }
    @Override
    public void setCheckSum(byte[] data) {
        int sum = 0;
        for (int i = 0; i < data.length - 2; ++i) {
            sum += data[i] & 0xFF;
        }

        this.checkSum = (byte) (sum % 256);
    }

    @Override
    public void setEtx() {
        this.etx = (byte) 0x03;
    }
}
