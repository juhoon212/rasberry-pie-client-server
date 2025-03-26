package org.example.aprocmd.util;

public class CommandUtil {

    public static final int ST_COMMAND_LENGTH = 16;
    public static final int[] ST_COMMAND_DATA_RANGE = new int[]{8, 14};
    public static final int[] MS_COMMAND_DATA_RANGE = new int[]{7, 13};
    public static final int[] PS_COMMAND_DATA_RANGE = new int[]{8, 457};

    public static final int[] STEP_HEADER_DATA_RANGE = new int[]{8, 37};
    public static final int[] STEP_DATA_DATA_RANGE = new int[]{37, 137};
    public static final int DATA_LENGTH = 2;
    public static final int LENGTH_POS = 4;
    // 추후 변경될 수 있음
    public static final byte[] DEFAULT_BLOCK = new byte[]{0x01, 0x01};

    public static final byte[] DEFAULT_PACKET_HEADER = new byte[]{0x02, (byte) 0xFE};

    public static final String HOST = "192.168.1.2";
    public static final int PORT = 9013;
}
