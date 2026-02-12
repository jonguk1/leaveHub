package com.example.leaveHub.controller.leave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.leaveHub.common.Criteria;
import com.example.leaveHub.constant.LeaveStatus;
import com.example.leaveHub.dto.PageDTO;
import com.example.leaveHub.service.leave.LeaveRequestService;
import com.example.leaveHub.vo.LeaveRequestVO;
import com.example.leaveHub.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    // 연차 신청 처리
    @PostMapping("/leave/insert")
    public String insertLeaveRequest(LeaveRequestVO vo,
            @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile, RedirectAttributes rttr,
            HttpSession session) {
        // 세션에서 로그인한 유저 정보 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        vo.setUserId(loginUser.getUserId());

        if (vo.getStatus() == null) {
            vo.setStatus(LeaveStatus.PENDING);
        }

        // 연차 신청 서비스 호출
        try {
            leaveRequestService.insertLeaveRequest(vo, uploadFile);
            rttr.addFlashAttribute("message", "연차 신청이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "연차 신청 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/employee";
    }

    // 내 연차 신청 조회
    @GetMapping("/employee")
    public String getMyLeaveRequests(Criteria cri, HttpSession session, Model model) {
        // 세션에서 로그인한 유저 정보 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        // 연차 신청 조회 서비스 호출
        try {
            // 페이징 처리된 전체 연차 요청 조회
            Map<String, Object> params = new HashMap<>();
            params.put("userId", loginUser.getUserId());
            params.put("offset", cri.getOffset());
            params.put("limit", cri.getAmount());

            int total = leaveRequestService.getLeaveRequestCount(loginUser.getUserId());

            List<LeaveRequestVO> requestList = leaveRequestService.getLeaveRequestsByUserId(params);
            model.addAttribute("requestList", requestList);
            model.addAttribute("pageMaker", new PageDTO(cri, total));

        } catch (Exception e) {
            model.addAttribute("errorMsg", "연차 신청 조회 중 오류가 발생했습니다: " + e.getMessage());

        }
        return "user/employee";
    }

    // 내 연차 수정
    @PostMapping("/leave/update")
    public String updateLeaveRequest(LeaveRequestVO vo, HttpSession session, RedirectAttributes rttr) {
        // 세션에서 로그인한 유저 정보 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        // 연차 수정 서비스 호출
        try {
            leaveRequestService.updateLeaveRequest(vo, loginUser.getUserId());
            rttr.addFlashAttribute("message", "연차 신청이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "연차 신청 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/employee";
    }

    // 내 연차 삭제
    @PostMapping("/leave/delete")
    public String deleteLeaveRequest(Long leaveId, RedirectAttributes rttr) {
        // 연차 삭제 서비스 호출
        try {
            leaveRequestService.deleteLeaveRequest(leaveId);
            rttr.addFlashAttribute("message", "연차 신청이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "연차 신청 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/employee";
    }

}
