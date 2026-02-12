<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />
<script src="<c:url value='/js/update.js' />"></script>

<body>
    <div id="loginScreen" class="login-container">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">회원 정보 수정</h1>
                <p style="font-size: 0.8rem; color: #666; text-align: center;">정보를 변경하거나 계정을 관리할 수 있습니다.</p>
            </div>

            <form id="updateForm" action="/user/update" method="post">
                <div class="form-group">
                    <label for="userName">이름</label>
                    <input type="text" id="userName" name="userName" 
                           value="${loginUser.userName}" placeholder="이름을 입력하세요">
                    <small id="nameMessage" style="color:red; display:none;"></small>
                </div>

                <div class="form-group">
                    <label for="currentPassword">기존 비밀번호</label>
                    <input type="password" id="currentPassword" name="currentPassword" placeholder="현재 비밀번호를 입력하세요" required class="form-control">
                </div>

                <div class="form-group">
                    <label for="password">새 비밀번호 <small>(변경 시에만 입력)</small></label>
                    <input type="password" id="password" name="password" placeholder="변경할 비밀번호를 입력하세요">
                </div>

                <div class="form-group">
                    <label for="passwordConfirm">새 비밀번호 확인</label>
                    <input type="password" id="passwordConfirm" placeholder="비밀번호를 한 번 더 입력하세요">
                    <small id="pwMessage" style="color:red; display:none;"></small>
                </div>

                <div class="button-group" style="display: flex; flex-direction: column; gap: 10px;">
                    <button type="submit" id="submitBtn" class="btn btn-primary btn-full" disabled>정보 수정</button>
                    <c:choose>
                        <c:when test="${loginUser.role == 'ADMIN'}">
                            <a href="/admin" class="btn btn-secondary btn-full" style="text-align: center; display: block; text-decoration: none;">취소</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/employee" class="btn btn-secondary btn-full" style="text-align: center; display: block; text-decoration: none;">취소</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
            <c:if test="${not empty errorMsg}">
                <div style="color: #e74c3c; background-color: #fceae9; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 0.85rem; text-align: center; border: 1px solid #f5c6cb;">
                    ${errorMsg}
                </div>
            </c:if>
            <div style="margin-top: 20px; border-top: 1px dashed #ccc; padding-top: 15px;">
                <button type="button" class="btn btn-danger btn-full" onclick="confirmDelete(event)">회원 탈퇴</button>
            </div>

            <form id="deleteForm" action="/user/delete" method="post" style="display:none;"></form>
        </div>
    </div>