package org.example.aprocmd.domain.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static org.example.aprocmd.util.CommandUtil.LENGTH_POS;
import static org.example.aprocmd.util.ExceptionUtil.ILLEGAL_ARGUMENT_DEFAULT_MESSAGE;

@Slf4j
public abstract class Command {

    @Getter protected CommandType commandType;
    @Getter protected byte[] command; // 총 data 헤더~etx 까지
    @Getter protected byte[] data; // only data area
    protected byte[] header;
    protected byte mainCommand;
    protected byte subCommand;
    protected byte[] temp;
    protected byte[] length; // 길이 2byte
    protected byte checkSum;
    protected byte etx;
    public void setHeader(byte[] data) {
        this.header[0] = data[0];
        this.header[1] = data[1];
    }

    protected void setMainCommand(byte[] data) {
        this.mainCommand = data[2];
    }

    protected void setSubCommand(byte[] data) {
        this.subCommand = data[3];
    }

    protected void setLength(byte[] bytes) {
        this.length[0] = bytes[LENGTH_POS];
        this.length[1] = bytes[LENGTH_POS + 1];
    }

    protected void setData(byte[] data) {}

    protected void setCheckSum(byte[] data) {
        int sum = 0;
        for (int i = 0; i < data.length - 2; ++i) {
            sum += data[i] & 0xFF;
        }

        this.checkSum = (byte) (sum % 256);
    }

    protected void setEtx() {
        this.etx = (byte) 0x03;
    }

    protected void validate(byte[] data) {
        final byte nullData = 0x00;

        if (data == null) {
            log.error("Command validate() : 데이터가 존재하지 않습니다.");
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        if (data[0] == nullData || data[1] == nullData) {
            log.error("Command validate() : 헤더가 존재하지 않습니다.");
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        // etx validation
        if (data[data.length-1] != 0x00) {
            log.error("Command validate() : ETX가 존재하지 않습니다.");
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }
    }




}
