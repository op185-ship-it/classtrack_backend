package com.classtrack.dto.request;

import java.util.List;
import java.util.UUID;

/**
 * Payload sent by Flask after face recognition is complete.
 * Flask sends back the sessionId and the roll numbers detected as present.
 */
public class FlaskAttendanceMarkRequestDto {
    private UUID sessionId;
    private List<Integer> presentRollNumbers;

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public List<Integer> getPresentRollNumbers() {
        return presentRollNumbers;
    }

    public void setPresentRollNumbers(List<Integer> presentRollNumbers) {
        this.presentRollNumbers = presentRollNumbers;
    }
}

