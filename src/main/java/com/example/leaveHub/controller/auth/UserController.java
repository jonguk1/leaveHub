package com.example.leaveHub.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.leaveHub.service.user.UserService;
import com.example.leaveHub.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 회원가입 기능
    @PostMapping("/register")
    public String register(UserVO userVO, RedirectAttributes rttr) {
        try {
            userService.register(userVO);
            rttr.addFlashAttribute("message", "회원가입이 성공하였습니다.관리자의 승인을 기다리세요");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "회원가입중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/";
    }

    // ID 중복체크
    @GetMapping("/checkId")
    @ResponseBody // 페이지 이동이 아닌 데이터를 반환하기 위함
    public int checkId(@RequestParam("userId") String userId) {
        return userService.existsByUserId(userId);
    }

    // 회원가입 페이지 이동
    @GetMapping("/register")
    public String registerMain() {
        return "auth/register";
    }

    // 회원수정 페이지 이동
    @GetMapping("/update")
    public String updateMain(String userId, HttpSession session, Model model) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        UserVO user = userService.getUserById(loginUser.getUserId());
        model.addAttribute("user", user);
        return "auth/update";
    }

    // 회원수정
    @PostMapping("/user/update")
    public String updateUser(UserVO userVO, String currentPassword, HttpSession session, RedirectAttributes rttr) {
        // 세션에서 로그인한 유저 정보 확인
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        userVO.setUserId(loginUser.getUserId());

        // 회원 수정 서비스 호출
        try {
            userService.updateUser(userVO, currentPassword);
            // 세션 동기화 (화면에 즉시 반영하기 위함)
            loginUser.setUserName(userVO.getUserName());
            rttr.addFlashAttribute("message", "정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "수정 중 오류 발생: " + e.getMessage());
        }

        return "ADMIN".equals(loginUser.getRole()) ? "redirect:/admin" : "redirect:/employee";
    }

    // 회원탈퇴
    @PostMapping("/user/delete")
    public String deleteUser(HttpSession session, RedirectAttributes rttr) {
        // 세션에서 로그인한 유저 정보 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        // 회원 탈퇴 서비스 호출
        try {
            String userId = loginUser.getUserId();
            userService.deleteUser(userId);
            session.invalidate();
            rttr.addFlashAttribute("message", "탈퇴가 성공적으로 처리되었습니다.");
            return "redirect:/";
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "삭제 오류가 발생했습니다: " + e.getMessage());
            return "ADMIN".equals(loginUser.getRole()) ? "redirect:/admin" : "redirect:/employee";
        }
    }

}
