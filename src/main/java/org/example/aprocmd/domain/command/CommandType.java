package org.example.aprocmd.domain.command;

import lombok.Getter;

@Getter
public enum CommandType {

    // MS, ST, PS, PR, PC, PM, UP, SS
    MS("MS","장비 상태 요청", new byte[]{(byte) 0x4d, (byte) 0x53}, 2,7, 15),
    ST("ST", "공정 시작 시간 전송", new byte[]{(byte) 0x53, (byte) 0x54}, 2, 8, 16),
    PS("PS", "Step 정보 전송 명령", new byte[]{(byte) 0x50, (byte) 0x53}, 2, 451, 459);

    private final String type;
    private final String detail;
    private final byte[] commandByte;
    private final int dataAreaLength;
    private final int commandLength;
    private final int totalLength; // 요청 시 command 총 길이
    private final byte endOfPacket;

    CommandType(String type, String detail, byte[] commandByte, int commandLength, int dataAreaLength, int totalLength) {
        this.type = type;
        this.detail = detail;
        this.commandByte = commandByte;
        this.dataAreaLength = dataAreaLength;
        this.commandLength = commandLength;
        this.totalLength = totalLength;
        this.endOfPacket = (byte) 0x03;
    }
}
