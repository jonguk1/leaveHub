package com.example.leaveHub.service.admin;

import java.util.List;
import java.util.Map;

import com.example.leaveHub.vo.LeaveRequestVO;
import com.example.leaveHub.vo.UserVO;

public interface AdminService {

    public boolean isAdmin(UserVO user);

    int getLeaveStatusCounts(String status);

    List<LeaveRequestVO> getAllLeaveRequests(Map<String, Object> params);

    void approveLeaveRequest(Long leaveId);

    void rejectLeaveRequest(Long leaveId, String rejectReason);

    void isApproved(String userId);

    List<UserVO> selectUsersByEnabled(Map<String, Object> params);

    int countByEnabledStatus();

    LeaveRequestVO getLeaveRequestById(Long leaveId);

}
