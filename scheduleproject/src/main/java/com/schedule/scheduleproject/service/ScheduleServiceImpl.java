package com.schedule.scheduleproject.service;

import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.entity.Schedule;
import com.schedule.scheduleproject.exception.InvalidPasswordException;
import com.schedule.scheduleproject.exception.ScheduleNotFoundException;
import com.schedule.scheduleproject.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
        schedule.setCreate_date(LocalDateTime.now());
        schedule.setUpdate_date(LocalDateTime.now());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public List<Schedule> getFilteredSchedules(String writer, LocalDate update_date){
        return scheduleRepository.findAllSchedules().stream()
                .filter(schedule -> {
                    boolean matchWriter = (writer == null || writer.equals(schedule.getWriter()));
                    boolean matchDate = (update_date == null || update_date.equals(schedule.getUpdate_date().toLocalDate()));
                    return matchWriter || matchDate;  // 하나라도 true면 해당 schedule 객체 포함
                })
                .sorted(Comparator.comparing(Schedule::getUpdate_date).reversed())
                .collect(Collectors.toList());
        // Stream API를 활용해서 Schedule 리스트를 필터링, 정렬, 리스트로 반환하는 파이프라인.
    }

    @Override
    public Schedule getScheduleById(Long id) { // Service 계층에선 사용자가 api 읽기 좋게 "get" 표현
        Schedule schedule =  scheduleRepository.findScheduleById(id);  // Repository 계층에선 실제 데이터를 찾는 "find"대로 표현
        if (schedule == null) {
            throw new ScheduleNotFoundException(id);
        }
        return schedule;
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findScheduleById(id); // 해당 id인 일정 찾기

        if (schedule == null) { // 먼저 null 체크
            throw new ScheduleNotFoundException(id);
        }
        if (!schedule.getPassword().equals(password)) { // 비밀번호 검증
            throw new InvalidPasswordException();
        }

        scheduleRepository.deleteSchedule(id);
    }

    @Override
    public Schedule updateSchedule (Long id, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findScheduleById(id); // 해당 id인 일정 찾기
        if (schedule == null) { // 먼저 null값 체크
            throw new ScheduleNotFoundException(id);
        }
        if (!schedule.getPassword().equals(dto.getPassword())) { // 비밀번호 검증
            throw new InvalidPasswordException();
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContent(dto.getContent());
        schedule.setWriter(dto.getWriter());
        schedule.setUpdate_date(LocalDateTime.now());
        return schedule;
    }
}
