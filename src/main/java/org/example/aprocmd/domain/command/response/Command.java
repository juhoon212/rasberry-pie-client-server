package org.example.aprocmd.domain.command.response;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.request.CommandType;

import static org.example.aprocmd.util.ExceptionUtil.ILLEGAL_ARGUMENT_DEFAULT_MESSAGE;

@Slf4j
public abstract class Command {

    @Getter protected CommandType commandType;
    protected byte[] header;

    protected byte mainCommand;
    protected byte subCommand;
    protected byte[] temp;
    protected byte[] length;
    @Getter protected byte[] data;

    protected byte checkSum;
    protected byte etx;
    protected byte[] totalData;
    void setHeader(byte[] data) {
        this.header[0] = data[0];
        this.header[1] = data[1];
    }

    void setMainCommand(byte[] data) {
        this.mainCommand = data[2];
    }

    void setSubCommand(byte[] data) {
        this.subCommand = data[3];
    }

    void setLength(byte[] data) {}

    void setData(byte[] data) {}

    void setCheckSum(byte[] data) {
        int sum = 0;
        for (int i = 0; i < data.length - 2; ++i) {
            sum += data[i] & 0xFF;
        }

        this.checkSum = (byte) (sum % 256);
    }

    void setEtx() {
        this.etx = (byte) 0x03;
    }

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
}
