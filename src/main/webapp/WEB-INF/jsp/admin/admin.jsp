<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<body>
    <!-- Admin Dashboard -->
    <div id="adminScreen" class="hidden">
        <div class="header">
            <div class="header-content">
                <div class="header-title">
                    <span>📅</span>
                    <span>연차 관리 시스템 (관리자)</span>
                </div>
                <div class="header-user">
                    <span id="adminUserName"></span>
                    <button class="btn btn-outline btn-sm" onclick="logout()">로그아웃</button>
                </div>
            </div>
        </div>

        <div class="container">
            <!-- 통계 -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-title">전체 신청</div>
                    <div class="stat-value" id="statTotal">0</div>
                </div>
                <div class="stat-card">
                    <div class="stat-title">대기 중</div>
                    <div class="stat-value yellow" id="statPending">0</div>
                </div>
                <div class="stat-card">
                    <div class="stat-title">승인됨</div>
                    <div class="stat-value green" id="statApproved">0</div>
                </div>
                <div class="stat-card">
                    <div class="stat-title">반려됨</div>
                    <div class="stat-value red" id="statRejected">0</div>
                </div>
            </div>

            <div class="card">
                <h2 style="margin-bottom: 0.5rem;">전체 연차 신청 내역</h2>
                <p class="card-description" style="margin-bottom: 1.5rem;">직원들의 연차 신청을 승인 또는 반려할 수 있습니다</p>

                <div class="tabs">
                    <div class="tab-list">
                        <button class="tab-button active" onclick="switchAdminTab('all')">전체 (<span
                                id="countAll">0</span>)</button>
                        <button class="tab-button" onclick="switchAdminTab('pending')">대기 (<span
                                id="countPending">0</span>)</button>
                        <button class="tab-button" onclick="switchAdminTab('approved')">승인 (<span
                                id="countApproved">0</span>)</button>
                        <button class="tab-button" onclick="switchAdminTab('rejected')">반려 (<span
                                id="countRejected">0</span>)</button>
                    </div>

                    <div id="adminRequestList"></div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>