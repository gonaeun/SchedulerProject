package com.schedule.scheduleproject.service;

import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.dto.ScheduleResponsetDto;
import com.schedule.scheduleproject.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Schedule saveSchedule(Schedule schedule); // 일정 생성, 수정

    List<Schedule> getAllSchedules();  // 필터 없이, 전체 일정 조회

    List<Schedule> getFilteredSchedules(String writer, LocalDate update_date); // 조건대로 전체 일정 조회

    Schedule getScheduleById(Long id);  // 단건 일정 조회

    void deleteSchedule(Long id, String password); // 일정 삭제
    // 해당 id인 일정을 찾아서. password가 일치하면 삭제해줘

    Schedule updateSchedule(Long id, ScheduleRequestDto dto);// 선택 일정 수정
    // 해당 id인 일정을 찾아서, 수정 요청 받은 DTO 정보대로 수정해줘
}
