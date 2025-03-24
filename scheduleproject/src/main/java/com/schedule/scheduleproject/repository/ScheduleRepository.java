package com.schedule.scheduleproject.repository;

import com.schedule.scheduleproject.entity.Schedule;
import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule); // 일정 생성, 수정

    List<Schedule> findAllSchedules();  // 전체 일정 조회

    Schedule findMemoById(Long id);  // 단건 일정 조회

    void deleteSchedule(Long id); // 일정 삭제
}
