package org.example.aprocmd.domain.command.ps;

import org.example.aprocmd.domain.command.request.ps.StepHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StepHeaderTest {

    @Test
    @DisplayName("step header 29byte를 넣으면 stepHeader가 완성되서 반환된다.")
    void stepHeaderTest1() throws Exception{
        //given
        byte[] data = new byte[] {
                0x02, (byte) 0xfe, 0x50, 0x53, (byte) 0xc3, 0x01, 0x01, 0x01, // data 영역 전
                0x01, 0x2A, 0x10, 0x3F, 0x05, 0x7E, 0x00, 0x11,
                (byte) 0xFF, (byte) 0x80, 0x4C, 0x55, 0x6D, 0x3B, 0x01,
                0x02, 0x03, 0x04, 0x05, (byte) 0xA0, (byte) 0xC3, 0x30,
                0x20, 0x18, 0x19, 0x22, 0x7F, 0x0A, 0x0B
        };
        //when
        StepHeader header = StepHeader.builder()
                .data(data)
                .build();
        //then
        assertThat(header.getMaxCur()).contains(0x3B, 0x01, 0x02, 0x03);

    }

}