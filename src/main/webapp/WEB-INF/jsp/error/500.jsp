<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<body>
    <div id="errorScreen" class="login-container">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">서버 오류 발생</h1>
                <p class="card-description">서버에서 문제가 발생했습니다.<br>잠시 후 다시 시도해 주세요.</p>
            </div>
            <div class="info-box" style="text-align: center; margin-bottom: 2rem;">
                <div class="info-value" style="color: #dc2626;">500 Internal Server Error</div>
                <div class="info-label">서버에 일시적인 문제가 있을 수 있습니다.</div>
            </div>  
            <a href="/" class="btn btn-primary btn-full" style="text-align: center; text-decoration: none; display: block;">
                메인으로 돌아가기
            </a>
        </div>
    </div>
</body>
</html>