package org.example.aprocmd.util;

public class CommandUtil {

    public static final int ST_COMMAND_LENGTH = 16;
    public static final int[] ST_COMMAND_DATA_RANGE = new int[]{8, 14};
    public static final int DATA_LENGTH = 2;
    public static final int LENGTH_POS = 4;
    // 추후 변경될 수 있음
    public static final byte[] DEFAULT_BLOCK = new byte[]{0x01, 0x01};

    public static byte[] DEFAULT_PACKET_HEADER = new byte[]{0x02, (byte) 0xFE};
}
