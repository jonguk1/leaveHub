package com.example.leaveHub.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.leaveHub.service.admin.AdminService;
import com.example.leaveHub.vo.LeaveRequestVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 관리자 메인 페이지
    @GetMapping("/admin")
    public String adminMain(@RequestParam(required = false) String status, Model model) {
        List<LeaveRequestVO> list;

        // 🔹 status 없으면 전체
        if (status == null) {
            list = adminService.getAllLeaveRequests();
        } else {
            list = adminService.getLeaveRequestsByStatus(status);
        }

        List<LeaveRequestVO> allList = adminService.getAllLeaveRequests();

        int countAll = allList.size();
        int countPending = 0;
        int countApproved = 0;
        int countRejected = 0;

        for (LeaveRequestVO req : allList) {
            String reqStatus = req.getStatus().name();

            if ("PENDING".equals(reqStatus))
                countPending++;
            else if ("APPROVED".equals(reqStatus))
                countApproved++;
            else if ("REJECTED".equals(reqStatus))
                countRejected++;
        }

        model.addAttribute("leaveList", list);
        model.addAttribute("currentStatus", status);

        model.addAttribute("countAll", countAll);
        model.addAttribute("countPending", countPending);
        model.addAttribute("countApproved", countApproved);
        model.addAttribute("countRejected", countRejected);

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

}
