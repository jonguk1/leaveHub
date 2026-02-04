package com.example.leaveHub.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.leaveHub.service.user.UserService;
import com.example.leaveHub.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 기능
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {
        UserVO user = userService.login(userId, password);
        if (user != null) {
            // 로그인 성공 처리
            session.setAttribute("loginUser", user);
            // 권한(Role)에 따른 분기 처리
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin"; // 관리자 페이지로
            } else {
                return "redirect:/employee"; // 직원 페이지로
            }
        } else {
            // 로그인 실패 처리
            model.addAttribute("errorMsg", "아이디 또는 비밀번호가 틀렸습니다.");
            return "auth/login";
        }
    }

    // 로그아웃 기능
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminMain() {
        return "user/admin";
    }

}
