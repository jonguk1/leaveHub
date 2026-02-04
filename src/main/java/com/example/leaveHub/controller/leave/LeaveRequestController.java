package com.example.leaveHub.controller.leave;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.leaveHub.constant.LeaveStatus;
import com.example.leaveHub.service.leave.LeaveRequestService;
import com.example.leaveHub.vo.LeaveRequestVO;
import com.example.leaveHub.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    // 연차 신청 처리
    @PostMapping("/leave/insert")
    public String insertLeaveRequest(LeaveRequestVO vo, RedirectAttributes rttr, HttpSession session) {
        // 세션에서 로그인한 유저 정보 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            rttr.addFlashAttribute("errorMsg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        vo.setUserId(loginUser.getUserId());
        if (vo.getStatus() == null) {
            vo.setStatus(LeaveStatus.PENDING);
        }

        // 연차 신청 서비스 호출
        try {
            leaveRequestService.insertLeaveRequest(vo);
            rttr.addFlashAttribute("message", "연차 신청이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "연차 신청 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/employee";
    }

    // 내 연차 신청 조회
    @GetMapping("/employee")
    public String getMyLeaveRequests(HttpSession session, Model model) {
        // 세션에서 로그인한 유저 정보 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            model.addAttribute("errorMsg", "로그인이 필요합니다.");
            return "redirect:/";
        }

        // 연차 신청 조회 서비스 호출
        try {
            List<LeaveRequestVO> requestList = leaveRequestService.getLeaveRequestsByUserId(loginUser.getUserId());
            model.addAttribute("requestList", requestList);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "연차 신청 조회 중 오류가 발생했습니다: " + e.getMessage());

        }
        return "user/employee";
    }

}
