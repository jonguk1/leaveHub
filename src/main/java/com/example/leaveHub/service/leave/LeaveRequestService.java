package com.example.leaveHub.service.leave;

import java.util.List;

import com.example.leaveHub.vo.LeaveRequestVO;

public interface LeaveRequestService {
    void insertLeaveRequest(LeaveRequestVO leaveRequest);

    List<LeaveRequestVO> getLeaveRequestsByUserId(String userId);
}
