package org.example.aprocmd.domain.command.request.ps;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.exception.command.CommandNotFoundException;

@Slf4j
@Getter
public class StepData {

    private byte commandType;
    private byte commandFormat;
    private byte mode;
    private byte[] registration = new byte[5];
    private byte[] parameter = new byte[10];
    private byte[] termination = new byte[72];
    private byte[] action = new byte[10];

    @Builder
    public StepData(byte[] data, int stepCount) {
        if (data == null) {
            log.error("StepData instance data is null");
            throw new CommandNotFoundException("데이터가 잘못되었습니다. 관리자에게 문의하세요.");
        }

        this.commandType = data[37];
        this.commandFormat = data[38];
        this.mode = data[39];
/*        this.setRegistration(data);
        this.setParameter(data);
        this.setTermination(data);
        this.setAction(data);
*/
    }



}
