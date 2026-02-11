package com.example.leaveHub.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.leaveHub.vo.LeaveRequestVO;

@Mapper
public interface AdminMapper {
    // 관리자 권한 확인
    int isAdmin(String userId);

    // 상황별 연차 요청 수
    int getLeaveStatusCounts(@Param("status") String status);

    // 전체 연차 요청 조회 (페이징)
    List<LeaveRequestVO> getAllLeaveRequests(Map<String, Object> params);

    // 연차 요청 승인
    int approveLeaveRequest(Long leaveId);

    // 연차 요청 거절
    int rejectLeaveRequest(@Param("leaveId") Long leaveId, @Param("rejectReason") String rejectReason);

    // 가입 승인
    int isApproved(@Param("userId") String userId);

}
