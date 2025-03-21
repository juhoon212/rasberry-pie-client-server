package org.example.aprocmd.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.example.aprocmd.util.ByteUtil.*;

class ByteUtilTest {

    @Test
    @DisplayName("byte를 16진수 String으로 변환할 수 있다.")
    void byteToHexStringTest() throws Exception{
        //given
        byte request = (byte) 0xFE;
        //when
        String result = byteToHexString(request);
        //then
        assertThat(result).isEqualTo("FE");
    }

    @Test
    @DisplayName("특정 String을 16진수로 변환할 수 있다.")
    void stringToHexStringTest() throws Exception{
        //given
        String request = "21";
        //when
        String result = numStringToHexString(request);
        //then
        assertThat(result).isEqualTo("15");
    }

    @Test
    @DisplayName("hex String 값을 byte 배열로 변환할 수 있다.")
    void hexStringToByteArrayTest() throws Exception{
        //given
        String request = "02fe";
        //when
        byte[] result = hexStringToByteArray(request);
        //then
        assertThat(result).contains((byte) 0x02, (byte) 0xfe);
    }

    @Test
    @DisplayName("byte 배열을 hex String으로 변환할 수 있다.")
    void byteArrayToHexStringTest() throws Exception{
        //given
        byte[] example = new byte[]{0x02, (byte) 0xfe};
        //when
        String result = byteArrayToHexString(example);
        //then
        assertThat(result).isEqualTo("02FE");
    }

}