package com.example.leaveHub.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 상황별 연차 요청 수
    @Override
    public int getLeaveStatusCounts(String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        return adminMapper.getLeaveStatusCounts(status);
    }

    // 전체 연차 요청 조회
    @Override
    public List<LeaveRequestVO> getAllLeaveRequests(Map<String, Object> params) {
        return adminMapper.getAllLeaveRequests(params);
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

    // 회원 승인
    @Override
    public void isApproved(String userId) {
        int result = adminMapper.isApproved(userId);
        if (result == 0) {
            throw new RuntimeException("회원 승인에 실패했습니다. ID: " + userId);
        }
    }

    // 승인 대기 유저 조회 (페이징)
    @Override
    public List<UserVO> selectUsersByEnabled(Map<String, Object> params) {
        return adminMapper.selectUsersByEnabled(params);
    }

    // 승인 대기 유저 요청 수
    @Override
    public int countByEnabledStatus() {
        return adminMapper.countByEnabledStatus();
    }

    // 파일 조회
    @Override
    public LeaveRequestVO getLeaveRequestById(Long leaveId) {
        return adminMapper.getLeaveRequestById(leaveId);
    }

}
