package com.schedule.scheduleproject.controller;


import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.dto.ScheduleResponseDto;
import com.schedule.scheduleproject.entity.Schedule;
import com.schedule.scheduleproject.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;
import com.schedule.scheduleproject.dto.ScheduleDeleteRequestDto;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public ScheduleResponseDto createSchedule(@RequestBody Schedule schedule) {
        Schedule saved = scheduleService.saveSchedule(schedule);
        return new ScheduleResponseDto(saved);
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
    public List<ScheduleResponseDto> getSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate update_date
    ) {
        List<Schedule> schedules;

        if (writer == null && update_date == null) {
            schedules = scheduleService.getAllSchedules();  // 필터링 조건이 없으면 전체 일정 조회
        } else {
            schedules = scheduleService.getFilteredSchedules(writer, update_date);
        }

        // List<엔터티> 데이터를 List<DTO>로 변환하는 표준 패턴
        // Schedule 엔터티 리스트를 클라이언트 응답용 DTO 리스트로 변환
        return schedules.stream().map(schedule -> new ScheduleResponseDto(schedule)).collect(Collectors.toList());
    }

    // 단건 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable long id) {
        return new ScheduleResponseDto(scheduleService.getScheduleById(id));
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(
            @PathVariable long id,
            @RequestBody ScheduleDeleteRequestDto dto
    ) {
        scheduleService.deleteSchedule(id, dto.getPassword());  // 비밀번호를 파라미터로 받지말고 DTO에서 꺼내
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto) {
        Schedule updated = scheduleService.updateSchedule(id, dto);
        return new ScheduleResponseDto(updated);
        // Service 계층에서 실제 로직 실행 후 뱉은 결과!
    }

}