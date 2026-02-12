package com.example.leaveHub.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class UserVO {
    private String userId;
    private String userName;
    private String password;
    private String role;
    private Date createdAt;
    private boolean enabled;
    private Date updatedAt;
    private Date deletedAt;
}
