package com.example.leaveHub.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.leaveHub.vo.LeaveRequestVO;

@Mapper
public interface AdminMapper {
    // 관리자 권한 확인
    int isAdmin(String userId);

    // 전체 연차 요청 조회
    List<LeaveRequestVO> getAllLeaveRequests();

    // 상태별 연차 요청 조회
    List<LeaveRequestVO> getLeaveRequestsByStatus(@Param("status") String status);

    // 연차 요청 승인
    int approveLeaveRequest(Long leaveId);

    // 연차 요청 거절
    int rejectLeaveRequest(@Param("leaveId") Long leaveId, @Param("rejectReason") String rejectReason);

}
