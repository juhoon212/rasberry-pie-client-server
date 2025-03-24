package org.example.aprocmd.service;

import org.assertj.core.api.Assertions;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.util.ByteUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import static org.example.aprocmd.util.ByteUtil.byteArrayToString;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RequestCommandServiceTest {

    @Autowired
    private RequestCommandService requestCommandService;

    @Test
    @DisplayName("ST 명령을 생성 할 수 있다.")
    void createPacketTest() throws Exception{
        //given
        byte[] command = requestCommandService.createPacket(
                Command.ST, LocalDateTime.of(2025, 3, 17, 13, 23, 36));
        //when
        System.out.println(command.toString());
        //then
        Assertions.assertThat(byteArrayToString(command)).isEqualTo("02fe5354080001011903110e23364503");
    }

}