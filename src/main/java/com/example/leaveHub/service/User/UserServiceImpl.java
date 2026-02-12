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

    // 회원수정
    @Override
    @Transactional
    public void updateUser(UserVO userVO, String currentPassword) {
        // 수정 전에 유저가 존재하는지 먼저 확인
        UserVO existingUser = userMapper.getUserById(userVO.getUserId());
        if (existingUser == null) {
            throw new RuntimeException("해당 사용자를 찾을 수 없습니다.");
        }

        // 2. 현재 비밀번호 확인
        if (!existingUser.getPassword().equals(currentPassword)) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 사용자가 '새 비밀번호' 칸을 비워뒀다면, DB에 있던 기존 비밀번호를 그대로 사용
        if (userVO.getPassword() == null || userVO.getPassword().trim().isEmpty()) {
            userVO.setPassword(existingUser.getPassword());
        }

        int result = userMapper.updateUser(userVO);
        if (result == 0) {
            throw new RuntimeException("회원수정에 실패하셨습니다");
        }
    }

    // 회원탈퇴
    @Override
    @Transactional
    public void deleteUser(String userId) {
        // 유저 상태 확인
        UserVO user = userMapper.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("이미 존재하지 않는 사용자입니다.");
        }

        // 삭제 확인
        if (user.getDeletedAt() != null) {
            throw new RuntimeException("이미 탈퇴된 계정입니다.");
        }

        int result = userMapper.deleteUser(userId);
        if (result == 0) {
            throw new RuntimeException("회원탈퇴에 실패하셨습니다");
        }
    }

    // 회원조회
    @Override
    public UserVO getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

}
