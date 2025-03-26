package org.example.aprocmd.domain.command.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.request.CommandType;
import org.example.aprocmd.exception.command.CommandNotFoundException;

import static org.example.aprocmd.util.CommandUtil.ST_COMMAND_DATA_RANGE;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class StartCommand extends Command {
    @Builder
    public StartCommand(byte[] data, CommandType commandType, byte[] totalData) {
        init();
        this.validate(data);

        this.commandType = commandType;
        this.setHeader(data);
        this.setMainCommand(data);
        this.setSubCommand(data);
        this.setData(data);
        this.setLength(data);
        this.totalData = totalData;
        this.setEtx();
    }

    private void init() {
        this.header = new byte[2];
        this.temp = new byte[]{0x01, 0x01};
        this.length = new byte[2];
        this.data = new byte[6];
    }

    @Override
    public void setData(byte[] requestData) {
        for (int i = ST_COMMAND_DATA_RANGE[0]; i < requestData.length - 2; ++i) {
            data[i - ST_COMMAND_DATA_RANGE[0]] = requestData[i];
        }
    }
}
