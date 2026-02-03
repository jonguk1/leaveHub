package com.example.leaveHub.service.User;

import org.springframework.stereotype.Service;

import com.example.leaveHub.mapper.UserMapper;
import com.example.leaveHub.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    // 로그인 기능
    @Override
    public UserVO login(String userId, String password) {
        return userMapper.login(userId, password);
    }

}
