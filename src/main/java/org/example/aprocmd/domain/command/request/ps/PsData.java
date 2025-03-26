package org.example.aprocmd.domain.command.request.ps;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PsData {
    private StepHeader stepHeader;
    private List<StepData> stepData = new ArrayList<>();

    @Builder
    public PsData(byte[] data) {
        this.stepHeader = StepHeader.builder()
                .data(data)
                .build();

        // TODO: StepData는 패킷 예제에서 CMD형식부터 문서랑 다름.

    }
}
