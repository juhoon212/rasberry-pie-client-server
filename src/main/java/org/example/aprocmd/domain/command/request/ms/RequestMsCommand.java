package org.example.aprocmd.domain.command.request.ms;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandType;

import static org.example.aprocmd.util.CommandUtil.MS_COMMAND_DATA_RANGE;

@Slf4j
@Getter
public final class RequestMsCommand extends Command {
    @Builder
    public RequestMsCommand(byte[] data, CommandType commandType) {
        init();
        this.validate(data);
        this.command = data;
        this.commandType = commandType;
        this.setHeader(data);
        this.setMainCommand(data);
        this.setSubCommand(data);
        this.setLength(data);
        this.setEtx();
    }

    private void init() {
        this.header = new byte[2];
        this.temp = new byte[]{0x01, 0x01};
        this.length = new byte[2];
    }

    @Override
    public void setData(byte[] requestData) {
        for (int i=MS_COMMAND_DATA_RANGE[0]; i<requestData.length-2; ++i) {
            data[i - MS_COMMAND_DATA_RANGE[0]] = requestData[i];
        }
    }
}
