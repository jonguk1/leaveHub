package com.example.leaveHub.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 회원가입
    @Override
    @Transactional
    public void register(UserVO userVO) {
        int result = userMapper.register(userVO);
        if (result == 0) {
            throw new RuntimeException("회원가입에 실패하셨습니다");
        }
    }

    // 중복체크
    @Override
    public int existsByUserId(String userId) {
        return userMapper.existsByUserId(userId);
    }

}
