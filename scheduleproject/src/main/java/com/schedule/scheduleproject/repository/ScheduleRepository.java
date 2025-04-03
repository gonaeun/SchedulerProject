package com.schedule.scheduleproject.repository;

import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.entity.Schedule;
import java.util.List;

public interface ScheduleRepository {
    // 이거 인터페이스니까 >> 이걸 implements ScheduleRepository한 클래스는 이 인터페이스의 모든 메서드를 꼭 구현해야 함!

    Schedule saveSchedule(Schedule schedule); // 일정 생성, 수정

    List<Schedule> findAllSchedules();  // 전체 일정 조회

    Schedule findScheduleById(Long id);  // 단건 일정 조회

    void deleteSchedule(Long id); // 일정 삭제

    Schedule updateSchedule(Schedule schedule); // 일정 수정
}
