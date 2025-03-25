package org.example.aprocmd.domain.command.response;

import lombok.Getter;
import org.example.aprocmd.domain.command.request.CommandType;

public interface Command {
    void setHeader(byte[] data);

    void setMainCommand(byte[] data, CommandType commandType);

    void setSubCommand(byte[] data, CommandType commandType);

    void setLength(byte[] data);

    void setData(byte[] data);

    void validate(byte[] data);

    void setCheckSum(byte[] data);

    void setEtx();

    byte[] getData();
}
