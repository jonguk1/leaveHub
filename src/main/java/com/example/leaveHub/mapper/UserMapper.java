package com.example.leaveHub.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.leaveHub.vo.UserVO;

@Mapper
public interface UserMapper {
    // ID와 PW로 사용자 조회
    UserVO login(@Param("userId") String userId, @Param("password") String password);

    // 회원가입
    int register(UserVO userVO);

    // 중복체크
    int existsByUserId(@Param("userId") String userId);
}
