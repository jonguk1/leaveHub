<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<body>
    <!-- Login Screen -->
    <div id="loginScreen" class="login-container">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">연차 관리 시스템</h1>
                <p class="card-description">로그인하여 연차를 관리하세요</p>
            </div>
            <form id="loginForm" action="/login" method="post">
                <div class="form-group">
                    <label for="loginUserId">아이디</label>
                    <input type="text" id="loginUserId" name="userId" placeholder="아이디를 입력하세요" required>
                </div>
                <div class="form-group">
                    <label for="loginPassword">비밀번호</label>
                    <input type="password" id="loginPassword" name="password" placeholder="비밀번호를 입력하세요" required>
                </div>
                <button type="submit" class="btn btn-primary btn-full">로그인</button>
            </form>
            <c:if test="${not empty errorMsg}">
                <div style="color: #e74c3c; background-color: #fceae9; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 0.85rem; text-align: center; border: 1px solid #f5c6cb;">
                    ${errorMsg}
                </div>
            </c:if>
        </div>
    </div>
</body>

</html>