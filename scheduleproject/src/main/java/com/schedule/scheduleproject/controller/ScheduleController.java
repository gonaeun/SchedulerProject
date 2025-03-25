package com.schedule.scheduleproject.controller;


import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.entity.Schedule;
import com.schedule.scheduleproject.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("/schedules")  // Prefix
public class ScheduleController {

    // 주입된 의존성을 변경할 수 없어서 객체의 상태를 안전하게 유지할 수 있음
    private final ScheduleService scheduleService;

    // 생성자 주입 : 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
    // 그 대상은 ScheduleService 의 구현체인 ScheduleServiceImpl
    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.saveSchedule(schedule);
    }

//    // 전체 일정 조회
//    @GetMapping
//    public List<Schedule> getAllSchedules() {
//        return scheduleService.getAllSchedules();
//    }
//
//    // 전체 일정 조회(필터링)
//    @GetMapping
//    public List<Schedule> getFilteredSchedules() {
//        return scheduleService.getFilteredSchedules();
//    }

    // 전체 일정 조회
    @GetMapping
    public List<Schedule> getSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate update_date
    ) { if (writer == null && update_date == null) {
        return scheduleService.getAllSchedules();  // 필터링 조건이 없으면 전체 일정 조회
    }
    return scheduleService.getFilteredSchedules(writer, update_date);
    }

    // 단건 일정 조회
    @GetMapping("/{id}")
    public Schedule getSchedule(@PathVariable long id) {
        return scheduleService.getScheduleById(id);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(
            @PathVariable long id,
            @RequestParam String password
    ) {
        scheduleService.deleteSchedule(id, password);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public Schedule updateSchedule(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto) {
        return scheduleService.updateSchedule(id, dto);
        // Service 계층에서 실제 로직 실힝 후 뱉은 결과!
    }

}