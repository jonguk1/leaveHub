package com.example.leaveHub.common;

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
            System.out.println("aaaaaa");
            response.sendRedirect("/");
            return false;
        }

        // 관리자 권한 확인
        if (uri.contains("/admin")) {
            if (!"ADMIN".equals(loginUser.getRole())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        return true;
    }
}
