package com.example.leaveHub.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.leaveHub.common.Criteria;
import com.example.leaveHub.dto.PageDTO;
import com.example.leaveHub.service.admin.AdminService;
import com.example.leaveHub.service.user.UserService;
import com.example.leaveHub.vo.LeaveRequestVO;
import com.example.leaveHub.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    // 관리자 메인 페이지
    @GetMapping("/admin")
    public String adminMain(Criteria cri, @RequestParam(required = false) String status, Model model,
            HttpSession session) {
        // 페이징 처리된 전체 연차 요청 조회
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("offset", cri.getOffset());
        params.put("limit", cri.getAmount());

        int total = adminService.getLeaveStatusCounts(status);
        int countAll = adminService.getLeaveStatusCounts(null);
        int pendingCount = adminService.getLeaveStatusCounts("PENDING");
        int approvedCount = adminService.getLeaveStatusCounts("APPROVED");
        int rejectedCount = adminService.getLeaveStatusCounts("REJECTED");

        List<LeaveRequestVO> leaveRequestList = adminService.getAllLeaveRequests(params);
        model.addAttribute("leaveRequestList", leaveRequestList);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        model.addAttribute("currentStatus", (status == null || status.isEmpty()) ? "ALL" : status);
        model.addAttribute("countAll", countAll);
        model.addAttribute("countPending", pendingCount);
        model.addAttribute("countApproved", approvedCount);
        model.addAttribute("countRejected", rejectedCount);

        return "admin/admin";

    }

    // 연차 요청 승인
    @PostMapping("/admin/approve")
    public String approveLeaveRequest(@RequestParam Long leaveId, HttpSession session,
            RedirectAttributes rttr) {
        // 연차 승인 처리
        try {
            adminService.approveLeaveRequest(leaveId);
            rttr.addFlashAttribute("success", "연차가 성공적으로 승인되었습니다.");
            return "redirect:/admin";
        } catch (RuntimeException e) {
            rttr.addFlashAttribute("error", "연차 승인에 실패했습니다.");
            return "redirect:/admin";
        }
    }

    // 연차 요청 거절
    @PostMapping("/admin/reject")
    public String rejectLeaveRequest(@RequestParam Long leaveId, HttpSession session,
            @RequestParam String rejectReason, RedirectAttributes rttr) {

        // 연차 거절 처리
        try {
            adminService.rejectLeaveRequest(leaveId, rejectReason);
            rttr.addFlashAttribute("success", "연차가 성공적으로 거절되었습니다.");
            return "redirect:/admin";
        } catch (RuntimeException e) {
            rttr.addFlashAttribute("error", "연차 거절에 실패했습니다.");
            return "redirect:/admin";
        }

    }

    // 회원 승인
    @PostMapping("/admin/approveUser")
    public String approveUser(@RequestParam("userId") String userId, RedirectAttributes rttr) {
        try {
            // 서비스에서 해당 ID의 enabled를 1로 바꾸는 로직 실행
            adminService.isApproved(userId);
            rttr.addFlashAttribute("msg", userId + "승인되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("error", "승인 처리 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/enabledUser";
    }

    // 회원 거절
    @PostMapping("/admin/rejectUser")
    public String rejectUser(String userId, String userName, HttpSession session, RedirectAttributes rttr) {
        try {
            // 회원 거절(삭제)
            userService.deleteUser(userId);
            rttr.addFlashAttribute("message", userName + " 님의 가입 요청을 거절했습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMsg", "처리 중 오류가 발생했습니다.");
        }

        return "redirect:/admin/enabledUser";
    }

    // 승인 대기 유저 조회 (페이징)
    @GetMapping("/admin/enabledUser")
    public String selectUsersByEnabled(Criteria cri, HttpSession session, Model model) {
        // 페이징처리
        Map<String, Object> params = new HashMap<>();
        params.put("offset", cri.getOffset());
        params.put("limit", cri.getAmount());

        // 서비스 호출
        List<UserVO> enabledUserList = adminService.selectUsersByEnabled(params);
        int total = adminService.countByEnabledStatus();

        model.addAttribute("userList", enabledUserList);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        model.addAttribute("total", total);

        return "admin/enabled";
    }

}
