package com.schedule.scheduleproject.exception;

// 사용자 정의 예외 클래스
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
