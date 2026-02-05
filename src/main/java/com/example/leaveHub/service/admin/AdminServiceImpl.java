package com.example.leaveHub.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leaveHub.mapper.AdminMapper;
import com.example.leaveHub.vo.LeaveRequestVO;
import com.example.leaveHub.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    // 관리자 권한 확인
    @Override
    public boolean isAdmin(UserVO user) {
        // 세션 만료 대비
        if (user == null || user.getUserId() == null) {
            return false;
        }

        int result = adminMapper.isAdmin(user.getUserId());
        return result == 1;
    }

    // 전체 연차 요청 조회
    @Override
    public List<LeaveRequestVO> getAllLeaveRequests() {
        return adminMapper.getAllLeaveRequests();
    }

    // 상태별 연차 요청 조회
    @Override
    public List<LeaveRequestVO> getLeaveRequestsByStatus(String status) {
        return adminMapper.getLeaveRequestsByStatus(status);
    }

    // 연차 요청 승인
    @Override
    @Transactional
    public void approveLeaveRequest(Long leaveId) {
        int result = adminMapper.approveLeaveRequest(leaveId);
        if (result == 0) {
            throw new RuntimeException("연차 승인에 실패했습니다. ID: " + leaveId);
        }
    }

    // 연차 요청 거절
    @Override
    public void rejectLeaveRequest(Long leaveId, String rejectReason) {
        int result = adminMapper.rejectLeaveRequest(leaveId, rejectReason);
        if (result == 0) {
            throw new RuntimeException("연차 거절에 실패했습니다. ID: " + leaveId);
        }
    }

}
