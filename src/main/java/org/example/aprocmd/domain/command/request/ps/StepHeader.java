package org.example.aprocmd.domain.command.request.ps;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.exception.command.CommandNotFoundException;
import org.example.aprocmd.util.CommandUtil;

import static org.example.aprocmd.util.CommandUtil.*;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StepHeader {
    private byte masterId;
    private byte channelId;
    private byte stepCount; // 총 step 개수
    private byte startStep; // 공정 시작 step 번호
    private byte endStep; // 공정 종료 step 번호 -> start step ~ endStep 까지 수행
    private byte[] maxVol = new byte[4]; // 최대 전압
    private byte[] minVol = new byte[4]; // 최소 전압
    private byte[] maxCur = new byte[4]; // 최대 전류
    private byte[] minCur = new byte[4]; // 최소 전류
    private byte[] maxCap = new byte[4]; // 최대 용량
    private byte[] maxTemp = new byte[4]; // 최대 온도



    @Builder
    public StepHeader(byte[] data) {
        if (data == null) {
            log.error("StepHeader instance data is null");
            throw new CommandNotFoundException("데이터가 잘못되었습니다. 관리자에게 문의하세요.");
        }

        int startIdx = STEP_HEADER_DATA_RANGE[0];

        this.masterId = data[startIdx];
        this.channelId = data[startIdx+1];
        this.stepCount = data[startIdx+2];
        this.startStep = data[startIdx+3];
        this.endStep = data[startIdx+4];

        this.setStepHeaderArrays(data, startIdx); // maxVol, minVol, maxCur, minCur, maxCap, maxTemp
    }

    // array 4 byte씩 계속 증가

    // 4byte
    private void setMaxVol(byte[] data, int index) {
        for (int i=0; i<maxVol.length; ++i) {
            maxVol[i] = data[i + index];
        }
    }
    // 4byte
    private void setMinVol(byte[] data, int index) {
        for (int i=0; i<minVol.length; ++i) {
            minVol[i] = data[i + index];
        }
    }

    // 4byte
    private void setMaxCur(byte[] data, int index) {
        for (int i=0; i<maxCur.length; ++i) {
            maxCur[i] = data[i + index];
        }
    }

    private void setMinCur(byte[] data, int index) {
        for (int i=0; i<minCur.length; ++i) {
            minCur[i] = data[i + index];
        }
    }

    private void setMaxCap(byte[] data, int index) {
        for (int i=0; i<maxCap.length; ++i) {
            maxCap[i] = data[i + index];
        }
    }

    private void setMaxTemp(byte[] data, int index) {
        for (int i=0; i<maxTemp.length; ++i) {
            maxTemp[i] = data[i + index];
        }
    }

    private void setStepHeaderArrays(byte[] data, int startIdx) {
        this.setMaxVol(data, startIdx + maxVol.length);
        startIdx += maxVol.length;
        this.setMinVol(data, startIdx + minVol.length);
        startIdx += minVol.length;
        this.setMaxCur(data, startIdx + maxCur.length);
        startIdx += maxCur.length;
        this.setMinCur(data, startIdx + minCur.length);
        startIdx += minCur.length;
        this.setMaxCap(data, startIdx + maxCap.length);
        startIdx += maxCap.length;
        this.setMaxTemp(data, startIdx + maxTemp.length);
    }
}
