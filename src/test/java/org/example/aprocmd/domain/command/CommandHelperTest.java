package org.example.aprocmd.domain.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CommandHelperTest {

    @Autowired
    private CommandHelper commandHelper;

    @Test
    @DisplayName("패킷 헤더는 0x02, 0xfe 이다")
    void createPacketHeaderTest() throws Exception{
        commandHelper.createPacketHeader(new byte[]{0x02, (byte) 0xfe});
    }

    @Test
    @DisplayName("메인 command와 sub command를 생성할 수 있다.")
    void createCommandTest() throws Exception{
        //given
        byte[] bytes = new byte[16];
        //when
        commandHelper.createCommand(bytes, Command.ST);
        //then
        assertThat(bytes[2]).isEqualTo(Command.ST.getCommandByte()[0]);
        assertThat(bytes[3]).isEqualTo(Command.ST.getCommandByte()[1]);
    }



}