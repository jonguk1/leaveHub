package com.example.leaveHub.vo;

import java.sql.Date;

import com.example.leaveHub.constant.LeaveStatus;

import lombok.Data;

@Data
public class LeaveRequestVO {
    private Long leaveId;
    private String userId;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String reason;
    private LeaveStatus status;
    private String rejectReason;
    private Date createdAt;
    private Date updatedAt;
    private UserVO userVO;
    private String originFileName;
    private String storedFileName;
    private String filePath;
}
