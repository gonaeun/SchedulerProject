package com.schedule.scheduleproject.repository;

import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private Map<Long, Schedule> scheduleList = new HashMap<>();  // scheduleList라는 HashMap에 데이터 저장 (key는 id, value는 Schedule 객체)

    @Override   // saveSchedule() 메서드 사용
    public Schedule saveSchedule(Schedule schedule) {  // 일정 저장 메서드
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet())+1; // 첫값은 1이고, 값이 있으면 가장 큰 id 값에 +1
        schedule.setId(scheduleId); // 윗줄의 id값을 schedule 객체의 id값으로 설정
        scheduleList.put(scheduleId, schedule); // 새로운 일정을 scheduleList라는 HashMap에 저장 (각각 키값, value값)
        return schedule;  // 저장한 schedule 객체 반환 (다음 로직에서 사용해야되니까!)
    }

    @Override
    public Schedule findScheduleById(Long id) {  // 단건 일정 조회
        return scheduleList.get(id); // id로 schedule을 찾아서 반환
    }

    @Override
    public List<Schedule> findAllSchedules() {
        return new ArrayList<>(scheduleList.values());  // HashMap의 value들을 List로 반환
    }

    @Override
    public void deleteSchedule(Long id){
        scheduleList.remove(id);  // id로 HashMap에서 삭제
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        return null;
    }
}
