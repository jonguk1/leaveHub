package com.example.leaveHub.service.leave;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leaveHub.mapper.LeaveRequestMapper;
import com.example.leaveHub.vo.LeaveRequestVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestMapper leaveRequestMapper;

    // 연차 신청 등록
    @Override
    @Transactional
    public void insertLeaveRequest(LeaveRequestVO leaveRequest) {
        int result = leaveRequestMapper.insertLeaveRequest(leaveRequest);
        if (result == 0) {
            throw new RuntimeException("연차 신청 등록에 실패했습니다.");
        }
    }

    // 내 연차 조회
    @Override
    public List<LeaveRequestVO> getLeaveRequestsByUserId(String userId) {
        return leaveRequestMapper.getLeaveRequestsByUserId(userId);
    }

}
