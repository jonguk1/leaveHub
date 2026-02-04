package com.example.leaveHub.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.leaveHub.vo.LeaveRequestVO;

@Mapper
public interface LeaveRequestMapper {
    // 연차 신청 등록
    int insertLeaveRequest(LeaveRequestVO leaveRequest);

    // 내 연차 조회
    List<LeaveRequestVO> getLeaveRequestsByUserId(String userId);

}
