package com.example.leaveHub.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.leaveHub.vo.UserVO;

@Mapper
public interface UserMapper {
    // 회원가입
    int register(UserVO userVO);

    // 중복체크
    int existsByUserId(@Param("userId") String userId);

    // 회원수정
    int updateUser(UserVO vo);

    // 회원삭제
    int deleteUser(@Param("userId") String userId);

    // 회원조회
    UserVO getUserById(@Param("userId") String userId);
}
