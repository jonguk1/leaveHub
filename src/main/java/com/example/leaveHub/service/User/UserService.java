package com.example.leaveHub.service.user;

import com.example.leaveHub.vo.UserVO;

public interface UserService {
    UserVO login(String userId, String password);

    void register(UserVO userVO);

    int existsByUserId(String userId);

}
