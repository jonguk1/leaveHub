<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<!-- Employee Dashboard -->
<div id="employeeScreen">
    <div class="header">
        <div class="header-content">
            <div class="header-title">
                <span>📅</span>
                <span>연차 관리 시스템</span>
            </div>
            <div class="header-user">
                <span id="employeeUserName"><strong>${loginUser.userName}</strong>님</span>
                <button class="btn btn-outline btn-sm" onclick="location.href='/logout'">로그아웃</button>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="card">
            <div class="tabs">
                <div class="tab-list">
                    <button class="tab-button active" onclick="switchEmployeeTab('apply')">연차 신청</button>
                    <button class="tab-button" onclick="switchEmployeeTab('mylist')">내 신청 내역</button>
                </div>

                <!-- 연차 신청 탭 -->
                <div id="applyTab" class="tab-content active">
                    <h2 style="margin-bottom: 0.5rem;">연차 신청하기</h2>
                    <p class="card-description" style="margin-bottom: 1.5rem;">신청한 연차는 관리자의 승인 후 확정됩니다</p>

                    <form id="leaveRequestForm">
                        <div class="form-group">
                            <label for="leaveType">연차 종류</label>
                            <select id="leaveType" required>
                                <option value="연차">연차</option>
                                <option value="반차">반차</option>
                                <option value="오전반차">오전반차</option>
                                <option value="오후반차">오후반차</option>
                                <option value="병가">병가</option>
                                <option value="경조사">경조사</option>
                            </select>
                        </div>

                        <div class="grid-2">
                            <div class="form-group">
                                <label for="startDate">시작일</label>
                                <input type="date" id="startDate" required>
                            </div>
                            <div class="form-group">
                                <label for="endDate">종료일</label>
                                <input type="date" id="endDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="reason">사유</label>
                            <textarea id="reason" rows="4" placeholder="연차 사용 사유를 입력하세요" required></textarea>
                        </div>

                        <button type="submit" class="btn btn-primary btn-full">신청하기</button>
                    </form>
                </div>

                <!-- 내 신청 내역 탭 -->
                <div id="mylistTab" class="tab-content">
                    <h2 style="margin-bottom: 0.5rem;">내 연차 신청 내역</h2>
                    <p class="card-description" style="margin-bottom: 1.5rem;">총 <span id="myRequestCount">0</span>건의 신청
                        내역</p>

                    <div id="myRequestList"></div>
                </div>
            </div>
        </div>
    </div>
</div>

</html>