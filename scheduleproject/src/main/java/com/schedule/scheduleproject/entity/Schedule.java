package com.schedule.scheduleproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // JAP에게 "이 클래스는 DB 테이블이에요!"라고 알려줌 (DB랑 mapping)
@Getter
@Setter

public class Schedule {

    @Id // PK 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK 값 자동생성 관리
    private Long id;

    @Column(nullable = false) // NOT NULL
    private String title; // 일정 제목

    @Column(nullable = false)
    private String writer; // 작성자명

    @Column(nullable = false)
    private String contents; // 일정 내용

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd") // String을 LocalDate으로 변환하는 포맷. 날짜 포맷 설정함
    private LocalDate plan_date;  // 일정 날짜

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false, updatable = false) // 작성일은 수정 안됨
    private LocalDateTime create_date; // 게시물 작성일

    @Column(nullable = false)
    private LocalDateTime update_date; // 게시물 수정일

    // 최초 입력시, 수정일과 작성일 동일
    @PrePersist  // Entity 처음 저장되기 직전에, 기본값 설정하는 역할
    public void prePersist(){
        this.create_date = LocalDateTime.now();
        this.update_date = LocalDateTime.now();
    }

}
