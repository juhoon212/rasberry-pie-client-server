package org.example.aprocmd.domain.command.request.st;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandType;

import java.time.LocalDateTime;

import static org.example.aprocmd.util.CommandUtil.ST_COMMAND_DATA_RANGE;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public final class RequestStartCommand extends Command {

    private final LocalDateTime startCommandTime; // command start time
    @Builder
    public RequestStartCommand(byte[] data, CommandType commandType, LocalDateTime startCommandTime) {
        init();
        this.validate(data); // validation
        this.startCommandTime = startCommandTime;
        this.command = data;
        this.commandType = commandType;
        this.setHeader(data);
        this.setMainCommand(data);
        this.setSubCommand(data);
        this.setData(data);
        this.setLength(data);
        this.setCheckSum(data);
        this.setEtx();
    }

    private void init() {
        this.header = new byte[2];
        this.temp = new byte[]{0x01, 0x01};
        this.length = new byte[2];
        this.data = new byte[CommandType.ST.getDataAreaLength()];
    }

    @Override
    public void setData(byte[] requestData) {
        for (int i = ST_COMMAND_DATA_RANGE[0]; i < requestData.length - 2; ++i) {
            data[i - ST_COMMAND_DATA_RANGE[0]] = requestData[i];
        }
    }
}
