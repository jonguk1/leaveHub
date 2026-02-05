package com.example.leaveHub.service.admin;

import java.util.List;

import com.example.leaveHub.vo.LeaveRequestVO;
import com.example.leaveHub.vo.UserVO;

public interface AdminService {

    public boolean isAdmin(UserVO user);

    List<LeaveRequestVO> getAllLeaveRequests();

    List<LeaveRequestVO> getLeaveRequestsByStatus(String status);

    void approveLeaveRequest(Long leaveId);

    void rejectLeaveRequest(Long leaveId, String rejectReason);

}
