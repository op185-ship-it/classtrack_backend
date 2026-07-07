package com.classtrack.dto.response;

import java.util.List;

public class FlaskAttendanceResponse {
    private Long scheduleId;
    private List<String> presentStudents;
    private Integer count;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<String> getPresentStudents() {
        return presentStudents;
    }

    public void setPresentStudents(List<String> presentStudents) {
        this.presentStudents = presentStudents;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
