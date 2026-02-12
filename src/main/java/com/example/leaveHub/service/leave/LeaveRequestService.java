package com.example.leaveHub.service.leave;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.leaveHub.vo.LeaveRequestVO;

public interface LeaveRequestService {
    void insertLeaveRequest(LeaveRequestVO leaveRequest, MultipartFile uploadFile);

    int getLeaveRequestCount(String userId);

    List<LeaveRequestVO> getLeaveRequestsByUserId(Map<String, Object> params);

    void updateLeaveRequest(LeaveRequestVO vo, String userId);

    void deleteLeaveRequest(Long leaveId);
}
