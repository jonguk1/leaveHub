<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<body>
    <div id="errorScreen" class="login-container">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">접근 권한 없음</h1>
                <p class="card-description">해당 페이지에 접근할 수 있는 권한이 없습니다.</p>
            </div>

            <div class="info-box" style="text-align: center; margin-bottom: 2rem;">
                <div class="info-value" style="color: #dc2626;">403 Forbidden</div>
                <div class="info-label">관리자 계정으로 로그인했는지 확인해 주세요.</div>
            </div>

            <a href="/" class="btn btn-primary btn-full" style="text-align: center; text-decoration: none; display: block;">
                메인으로 돌아가기
            </a>
        </div>
    </div>
</body>
</html>