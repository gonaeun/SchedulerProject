package com.schedule.scheduleproject.dto;

// 서버가 클라이언트에게 응답 보낼 때 사용
// DB에서 꺼낸 모든 필드 (보안 때문에 password 제외)

import com.schedule.scheduleproject.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponsetDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDate plan_date;
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    public ScheduleResponsetDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.writer = schedule.getWriter();
        this.plan_date = schedule.getPlan_date();
        this.create_date = schedule.getCreate_date();
        this.update_date = schedule.getUpdate_date();
    }
}
