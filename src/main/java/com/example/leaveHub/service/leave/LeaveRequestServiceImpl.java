package com.example.leaveHub.service.leave;

import java.util.List;
import java.util.Map;

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

    // 내 연차 갯수 조회
    @Override
    public int getLeaveRequestCount(String userId) {
        return leaveRequestMapper.getLeaveRequestCount(userId);
    }

    // 내 연차 조회
    @Override
    public List<LeaveRequestVO> getLeaveRequestsByUserId(Map<String, Object> params) {
        return leaveRequestMapper.getLeaveRequestsByUserId(params);
    }

    // 내 연차 수정
    @Override
    @Transactional
    public void updateLeaveRequest(LeaveRequestVO vo, String userId) {
        int result = leaveRequestMapper.updateLeaveRequest(vo, userId);
        if (result == 0) {
            throw new RuntimeException("수정 권한이 없거나, 이미 처리된 신청 건입니다.");
        }
    }

    // 내 연차 삭제
    @Override
    @Transactional
    public void deleteLeaveRequest(Long leaveId) {
        int result = leaveRequestMapper.deleteLeaveRequest(leaveId);
        if (result == 0) {
            throw new RuntimeException("연차 삭제에 실패했습니다.");
        }
    }

}
