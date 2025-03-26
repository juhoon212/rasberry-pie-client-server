package org.example.aprocmd.domain.command.request.pc;

import lombok.Builder;
import org.example.aprocmd.domain.command.Command;
import org.example.aprocmd.domain.command.CommandType;

public class RequestPcCommand extends Command {

    @Builder
    public RequestPcCommand(byte[] data, CommandType commandType) {
        this.validate(data);
        this.command = data;
        this.setHeader(data);
        this.setMainCommand(data);
        this.setSubCommand(data);
        this.setLength(data);
        this.setEtx();
    }
}
