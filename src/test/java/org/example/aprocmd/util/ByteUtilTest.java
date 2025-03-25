package org.example.aprocmd.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.example.aprocmd.util.ByteUtil.*;
import static org.example.aprocmd.util.ByteUtil.byteToHexString;

class ByteUtilTest {

    @Test
    @DisplayName("byte를 16진수 String으로 변환할 수 있다.")
    void byteToHexStringTest() throws Exception{
        //given
        byte request = (byte) 0xFE;
        //when
        //then
        StepVerifier.create(byteToHexString(request))
                .expectNext("FE")
                .verifyComplete();
    }





}