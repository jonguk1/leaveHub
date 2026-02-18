package com.example.leaveHub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.leaveHub.constant.LeaveStatus;
import com.example.leaveHub.service.leave.LeaveRequestService;
import com.example.leaveHub.vo.LeaveRequestVO;

import java.sql.Date;

@SpringBootTest
class LeaveRequestTest {

    @Autowired
    private LeaveRequestService leaveService;

    @Test
    void 중복_연차_신청_방지_테스트() throws Exception {

        // 1. 테스트 데이터 준비
        LeaveRequestVO req = new LeaveRequestVO();
        req.setUserId("user01");
        req.setStartDate(Date.valueOf("2099-01-01"));
        req.setEndDate(Date.valueOf("2099-01-05"));
        req.setLeaveType("연차");
        req.setReason("테스트");
        req.setStatus(LeaveStatus.PENDING);

        // 첫 번째 신청
        leaveService.insertLeaveRequest(req, null);

        // 두 번째 신청 (에러를 잡아내야 하는 구간)
        Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            leaveService.insertLeaveRequest(req, null);
        });

        // 검증
        org.assertj.core.api.Assertions.assertThat(exception.getMessage())
                .contains("이미 동일한 기간에 연차 신청이 존재합니다");
    }
}