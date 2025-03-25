package org.example.aprocmd.service.dto.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.response.Command;
import org.example.aprocmd.service.dto.ResponseCommandDto;
import org.example.aprocmd.util.ByteUtil;
import org.example.aprocmd.util.DateUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandDtoMapper {

    public ResponseCommandDto mapToCommandResponseDto(final Command command) {
        return new ResponseCommandDto(
                ByteUtil.byteArrayToHexString(command.getData()),
                DateUtil.getCurrentLocalDateTime());
    }
}
