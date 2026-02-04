package com.example.leaveHub.constant;

import lombok.Getter;

@Getter
public enum LeaveStatus {
    PENDING("대기"),
    APPROVED("승인"),
    REJECTED("반려");

    private final String description;

    LeaveStatus(String description) {
        this.description = description;
    }
}
