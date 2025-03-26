package org.example.aprocmd.domain.command.request.ps;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandType;

import static org.example.aprocmd.util.CommandUtil.PS_COMMAND_DATA_RANGE;
import static org.example.aprocmd.util.ExceptionUtil.ILLEGAL_ARGUMENT_DEFAULT_MESSAGE;

@Slf4j
@Getter
public final class RequestPsCommand extends Command {

    private PsData psData; // Data 특수

    @Builder
    public RequestPsCommand(byte[] data, CommandType commandType) {
        init();
        this.validate(data);

        this.psData = PsData.builder()
                .data(data)
                .build();
        this.command = data;
        this.commandType = commandType;
        this.setHeader(data);
        this.setMainCommand(data);
        this.setSubCommand(data);
        this.setData(data);
        this.setLength(data);
        this.setEtx();
    }
    private void init() {
        this.header = new byte[2];
        this.temp = new byte[]{0x01, 0x01};
        this.length = new byte[2];
        this.data = new byte[CommandType.PS.getTotalLength()];

    }


    @Override
    public void setData(byte[] requestData) {
        if (requestData.length != CommandType.PS.getTotalLength()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DEFAULT_MESSAGE);
        }

        for (int i=PS_COMMAND_DATA_RANGE[0]; i<requestData.length-2; ++i) {
            data[i - PS_COMMAND_DATA_RANGE[0]] = requestData[i];
        }
    }
}
