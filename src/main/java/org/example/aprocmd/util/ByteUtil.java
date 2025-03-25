package org.example.aprocmd.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
public class ByteUtil {

    public static Mono<String> byteToHexString(byte b) {
        return Mono.just(String.format("%02X", b));
    }

    // 10진수를 16진수 스트링으로 ex) "21" -> "15"
    public static String numStringToHexString(String s) {
        return String.format("%X", Integer.parseInt(s));
    }

    public static byte[] intToHexString(int data) {
        byte[] result = new byte[2];
        result[0] = (byte) ((data >> 8) & 0xFF);
        result[1] = (byte) (data & 0xFF);
        return result;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
            log.info("data: " + data[i / 2]);
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] stringDateToByteArray(String date) {
        if (date.length() % 2 != 0) {
            throw new IllegalArgumentException("입력 문자열 길이는 짝수여야 합니다.");
        }

        int length = date.length() / 2;
        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            String hexString = String.format("%02X", Integer.parseInt(date.substring(i * 2, i * 2 + 2)));
            result[i] = (byte) Integer.parseInt(hexString, 16);
        }

        return result;
    }

    public static byte[] localDateTimeToHexString(LocalDateTime startTime) {
        String parsedStartTime = DateUtil.localDateTimeToStringYearDivide(startTime); // "yy-MM-dd HH:mm:ss"
        String[] divideDateAndTime = parsedStartTime.split(" "); // ["yy-MM-dd", "HH:mm:ss"]

        StringBuilder sb = new StringBuilder();
        divideDateAndTime[0].replace("-", "").chars().forEach(c -> sb.append((char) c));
        divideDateAndTime[1].replace(":", "").chars().forEach(c -> sb.append((char) c));
        log.info("Start commandType time: " + sb);
        // convert to hex
        return stringDateToByteArray(sb.toString());
    }


}
