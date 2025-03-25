package org.example.aprocmd.infrastructure.dto.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.infrastructure.dto.SocketResponseDto;
import org.example.aprocmd.util.ByteUtil;
import org.example.aprocmd.util.DateUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketResponseMapper {

    public SocketResponseDto mapToSocketResponseDto(final byte[] response) {
        return new SocketResponseDto(
                ByteUtil.byteArrayToHexString(response), DateUtil.getCurrentLocalDateTime());
    }
}
