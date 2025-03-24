package com.schedule.scheduleproject.service;

import com.schedule.scheduleproject.entity.Schedule;
import com.schedule.scheduleproject.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 스프링 빈으로 등록
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
        // 생성자 주입
        // Service 클래스에서 Repository 사용하려면, Repository를 클래스 안에 넣어줘야함
        // 이렇게 하면, 스프링이 자동으로 Repository Bean 주입
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public Schedule getScheduleById(Long id) { // Service 계층에선 사용자가 api 읽기 좋게 "get" 표현
        return scheduleRepository.findScheduleById(id);  // Repository 계층에선 실제 데이터를 찾는 "find"대로 표현
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteSchedule(id);
    }
}
