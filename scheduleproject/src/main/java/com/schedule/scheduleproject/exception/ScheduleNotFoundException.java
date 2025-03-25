package com.schedule.scheduleproject.exception;

// 사용자 정의 예외 클래스
public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("일정 ID" + id + "를 찾을 수 없습니다.");
    }
}
