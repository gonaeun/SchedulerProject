# 🗓️ 일정 관리 앱
 

## 📌 프로젝트 개요
- **목표** : JDBC를 활용한 CRUD 구현을 목표로 하는 **일정 관리 어플리케이션**입니다.
- **기간** : 2025.03.21 ~ 2025.03.26
- **인원** : 1인 개발 (개인 프로젝트)
- **기술스택** : Java, Spring Boot

---

## 🪜 진행 사항
- [x] API 명세와 ERD 설계
- [x] 일정 생성
- [x] 전체 일정 조회 및 선택 일정 조회
- [x] 일정 수정 및 삭제
- [ ] 연관 관계 설정
- [ ] 페이지네이션
- [x] 예외 처리 핸들러
- [ ] 유효성 검사

---

## 🎯 API 명세
![image](https://github.com/user-attachments/assets/3cac4ce2-c95c-4f41-8fe9-34e1b126bb09)

🎨 https://documenter.getpostman.com/view/43269199/2sAYkKKJ8j 에서 확인 가능

---

## 🎯 스키마 정의서 (Schedule 테이블)
![image](https://github.com/user-attachments/assets/b54e3f18-6817-47eb-bea0-2541182b1e8c)

---

## 🎯 ERD 다이어그램
![image](https://github.com/user-attachments/assets/5cc072b8-16ac-4b23-b80b-4f4ecb2ff6db)

---

## 🌕 프로젝트 회고

테이블 개수를 늘리려 하니까 연결이 복잡하다. 코드를 새롭게 생성하는것보다 기존 코드를 리팩토링해가는 과정이 더 어려운 것 같다.. DTO는 대체 뭔가 싶었는데 막상 써보니까 왜 필요한지 명확히 알겠다! 인증 때문에 password는 필요하되 보안문제로 response 화면에는 안보이게 하고 싶어서 ScheduleDeleteRequestDto를 만든게 진짜 유용했다. 일단 부딪쳐봐야 Spring에 익숙해져갈 수 있나봐... 유효성 검사도 하고 싶었는데 지금 build에서 의존성을 추가해도 실행이 안되서 못하고 있다... 조만간 해결해봐야지
기존에 전체 일정을 조회하는 메서드가 있는데, 필터링을 거쳐서 전체 일정을 조회하는 기능을 추가해야해서 메서드를 어떻게 구성할지 고민이 있었다. 단건조회메서드는 getSchedule(), 전체일정조회메서드는 getSchedules()로 하되, 전체일정조회 메서드 안에서 조건에 따라 필터링 하도록 하였다 (writer, update_date둘다 null이면 전체조회/ 하나라도 있으면 필터링 조회 하도록) 그래서 현재 전체 일정 메서드에서도 그냥 /schedule로 조회하느냐, param 형식으로 필터링하느냐에 따라서 url이 다르다. 메서드를 또 나누기엔 번거롭고, 잘 구분지은 것 같다.

---
