package org.example.aprocmd.domain.command;

import org.example.aprocmd.domain.command.request.CommandType;
import org.example.aprocmd.domain.command.response.StartCommand;
import org.example.aprocmd.util.ByteUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class StartCommandTestEnum {



    @Test
    @DisplayName("byte[] 배열로 startCommand를 만들 수 있다.")
    void startCommandTest() throws Exception{
        //given
        String standardData = "02fe5354080001011903110e23364503";
        //when
        byte[] bytes = ByteUtil.hexStringToByteArray(standardData);

        StartCommand startCommand = StartCommand.builder()
                .data(bytes)
                .commandType(CommandType.ST)
                .build();
        //then
        assertThat(startCommand.getMainCommand()).isEqualTo((byte) 0x53);
        assertThat(startCommand.getData()[1]).isEqualTo((byte) 0x03);
    }

}