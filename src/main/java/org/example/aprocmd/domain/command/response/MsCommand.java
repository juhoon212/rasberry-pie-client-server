package org.example.aprocmd.domain.command.response;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.request.CommandType;

import static org.example.aprocmd.util.CommandUtil.ST_COMMAND_DATA_RANGE;

@Slf4j
@Getter
public final class MsCommand extends Command{
    @Builder
    public MsCommand(byte[] data, CommandType commandType) {
        this.validate(data);

        this.commandType = commandType;
        this.setHeader(data);
        this.setMainCommand(data);
        this.setSubCommand(data);
        this.setData(data);
        this.setLength(data);
        this.totalData = data;
        this.setEtx();
    }

    @Override
    public void setData(byte[] requestData) {
        for (int i=ST_COMMAND_DATA_RANGE[0]; i<requestData.length-2; ++i) {
            data[i - ST_COMMAND_DATA_RANGE[0]] = requestData[i];
        }
    }
}
