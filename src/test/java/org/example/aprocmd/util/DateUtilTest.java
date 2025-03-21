package org.example.aprocmd.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.*;

class DateUtilTest {

    private final Logger log = Logger.getLogger(DateUtilTest.class.getName());


    @Test
    @DisplayName("LocalDateTime을 yy-mm-dd HH:mm:ss 형식의 String으로 변환할 수 있다.")
    void localDateTimeToStringYearDivide() throws Exception{
        //given
        //when
        String result = DateUtil.localDateTimeToStringYearDivide(
                LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        //then
        log.info(result);
        assertThat(result).isEqualTo("21-01-01 00:00:00");
    }

}