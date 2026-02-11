<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />
<script src="<c:url value='/js/register.js' />"></script>

<body>
    <!-- Login Screen -->
    <div id="loginScreen" class="login-container">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">회원가입</h1>
            </div>
            <form id="registerForm" action="/register" method="post">
                <div class="form-group">
                    <label for="userId">아이디</label>
                    <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요">
                    <button type="button" id="checkIdBtn" class="btn btn-primary" style="white-space: nowrap;">중복체크</button>
                    <small id="idMessage" style="color:red; display:none;"></small>
                </div>
                <div class="form-group">
                    <label for="userName">이름</label>
                    <input type="text" id="userName" name="userName" placeholder="이름을 입력하세요">
                    <small id="nameMessage" style="color:red; display:none;"></small>
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요">
                </div>
                <div class="form-group">
                    <label for="passwordConfirm">비밀번호 확인</label>
                    <input type="password" id="passwordConfirm" placeholder="비밀번호를 한 번 더 입력하세요">
                    <small id="pwMessage" style="color:red; display:none;"></small>
                </div>
                <button type="submit" class="btn btn-primary btn-full" disabled>회원가입</button>
            </form>
            <div style="margin-top: 10px;">
                <a href="/" class="btn btn-danger btn-full">취소</a>
            </div>
            <c:if test="${not empty errorMsg}">
                <div style="color: #e74c3c; background-color: #fceae9; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 0.85rem; text-align: center; border: 1px solid #f5c6cb;">
                    ${errorMsg}
                </div>
            </c:if>
        </div>
    </div>
</body>

</html>