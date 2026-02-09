<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<body>
    <div id="errorScreen" class="login-container">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">페이지를 찾을 수 없습니다</h1>
                <p class="card-description">입력하신 주소가 잘못되었거나,<br>페이지가 삭제되어 이동되었을 수 있습니다.</p>
            </div>

            <div class="info-box" style="text-align: center; margin-bottom: 2rem;">
                <div class="info-value" style="color: #dc2626;">404 Not Found</div>
                <div class="info-label">존재하지 않는 페이지입니다.</div>
            </div>

            <a href="/" class="btn btn-primary btn-full" style="text-align: center; text-decoration: none; display: block;">
                메인으로 돌아가기
            </a>
        </div>
    </div>
</body>
</html>