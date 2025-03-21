package org.example.aprocmd.domain.command;

import lombok.Getter;

@Getter
public enum Command {

    MS("MS", new byte[]{(byte) 0x02, (byte) 0xFE}, 2,8, 15),
    ST("ST", new byte[]{0x53, 0x54}, 2, 10, 16);

    private final String type;
    private final byte[] commandByte;
    private final int commandLength;
    private final int dataLength;
    private final int totalLength;
    private final byte endOfPacket;

    Command(String type, byte[] commandByte, int dataLength, int commandLength, int totalLength) {
        this.type = type;
        this.commandByte = commandByte;
        this.commandLength = commandLength;
        this.dataLength = dataLength;
        this.totalLength = totalLength;
        this.endOfPacket = (byte) 0x03;
    }
}
