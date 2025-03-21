package org.example.aprocmd.domain.command;

import org.assertj.core.api.Assertions;
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
    @DisplayName("특정 문자열을 16진수로 변환할 수 있다.")
    void startTimeToHex() throws Exception{

    }

}