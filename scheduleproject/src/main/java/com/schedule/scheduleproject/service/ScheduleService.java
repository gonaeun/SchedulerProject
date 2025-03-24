package com.schedule.scheduleproject.service;

import com.schedule.scheduleproject.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule saveSchedule(Schedule schedule); // 일정 생성, 수정

    List<Schedule> getAllSchedules();  // 전체 일정 조회

    Schedule getScheduleById(Long id);  // 단건 일정 조회

    void deleteSchedule(Long id); // 일정 삭제

    // 선택 일정 수정은 나중에 작성하겠음

}
