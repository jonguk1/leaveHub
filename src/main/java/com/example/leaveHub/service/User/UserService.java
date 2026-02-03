package com.example.leaveHub.service.User;

import com.example.leaveHub.vo.UserVO;

public interface UserService {
    UserVO login(String userId, String password);

}
