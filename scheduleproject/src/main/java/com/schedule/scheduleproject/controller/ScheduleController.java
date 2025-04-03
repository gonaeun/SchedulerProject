package com.schedule.scheduleproject.controller;


import com.schedule.scheduleproject.dto.ScheduleDeleteRequestDto;
import com.schedule.scheduleproject.dto.ScheduleRequestDto;
import com.schedule.scheduleproject.dto.ScheduleResponseDto;
import com.schedule.scheduleproject.entity.Schedule;
import com.schedule.scheduleproject.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController // @Controller + @ResponseBody
@RequestMapping("/schedules")  // Prefix url 설정할 때 사용
public class ScheduleController {

    // 주입된 의존성을 변경할 수 없어서 객체의 상태를 안전하게 유지할 수 있음
    // final : 최초로 주입된 scheduleService가 계속 유지될거야!
    private final ScheduleService scheduleService;

    // 생성자 주입 : 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
    // 그 대상은 ScheduleService 의 구현체인 ScheduleServiceImpl
    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody Schedule schedule) {
        Schedule saved = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(new ScheduleResponseDto(saved), HttpStatus.CREATED);
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
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate update_date
    ) {
        List<Schedule> schedules; // 초기화

        if (writer == null && update_date == null) {
            schedules = scheduleService.getAllSchedules();  // 필터링 조건이 없으면 전체 일정 조회
        } else {
            schedules = scheduleService.getFilteredSchedules(writer, update_date);
        }

        // stream api를 사용해서 List<엔터티> 데이터를 List<DTO>로 변환
        // return schedules.stream().map(schedule -> new ScheduleResponseDto(schedule)).collect(Collectors.toList());

        // 일단 stream api보다 이해하기 쉬운 for 루프 사용
        List<ScheduleResponseDto> responseList = new ArrayList<>(); // 리스트 초기화 (ResponseEntity<> 코드로 바꿀 예정)
        for (Schedule schedule : schedules) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
            responseList.add(responseDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    // 단건 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable long id) {
        Schedule schedule = scheduleService.getScheduleById(id);

        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 NotFound 반환
        }

        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable long id,
            @RequestBody @Valid ScheduleDeleteRequestDto dto
    ) {
        scheduleService.deleteSchedule(id, dto.getPassword());  // 비밀번호를 파라미터로 받지말고 DTO에서 꺼내
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto) {

        Schedule schedule = scheduleService.getScheduleById(id);

//        // NullPointerException(NPE)방지
//        if (schedule == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // 필수값 검증
//        if (dto.getTitle() == null || dto.getContent() == null || dto.getWriter() == null || dto.getPassword() == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto), HttpStatus.OK);
        // Service 계층에서 실제 로직 실행 후 뱉은 결과!
    }

    // 일정 일부 수정(pw 없이 제목만 수정 가능)
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateTitle(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto) {

        Schedule schedule = scheduleService.getScheduleById(id);

//        // NullPointerException(NPE)방지
//        if (schedule == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // 필수값 검증 (제목 필수)
//        if (dto.getTitle() == null || dto.getContent() != null || dto.getWriter() != null || dto.getPassword() != null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        schedule.updateTitle(dto);
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }
}