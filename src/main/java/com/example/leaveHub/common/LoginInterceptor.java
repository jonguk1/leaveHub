package com.example.leaveHub.common;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.leaveHub.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();

        // 컨트롤러 요청이 아니면 그냥 통과
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        UserVO loginUser = (session != null) ? (UserVO) session.getAttribute("loginUser") : null;

        // 로그인 여부 확인
        if (loginUser == null) {
            alertAndRedirect(response, "로그인이 필요합니다.", "/");
            return false;
        }

        // 승인 여부 확인
        if (!loginUser.isEnabled()) {
            // 미승인 사용자의 세션 제거
            if (session != null) {
                session.invalidate();
            }
            alertAndRedirect(response, "관리자의 승인이 필요한 계정입니다.", "/");
            return false;
        }

        // 관리자 권한 확인
        if (uri.contains("/admin")) {
            if (!"ADMIN".equals(loginUser.getRole())) {
                alertAndRedirect(response, "관리자 전용 메뉴입니다. 접근 권한이 없습니다.", "/");
                return false;
            }
        }

        return true;
    }

    // 알림창 띄우기
    private void alertAndRedirect(HttpServletResponse response, String msg, String url) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + msg + "');");
        out.println("location.href='" + url + "';");
        out.println("</script>");
        out.flush();
        out.close(); // 자원 해제
    }
}
