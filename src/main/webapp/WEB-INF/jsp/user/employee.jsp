<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/jsp/common/head.jsp" />
<script src="<c:url value='/js/employee.js' />"></script>

<!-- Employee Dashboard -->
    <div id="employeeScreen">
        <div class="header">
            <div class="header-content">
                <div class="header-title">
                    <span>📅</span>
                    <span>연차 관리 시스템</span>
                </div>
                <div class="header-user">
                    <span id="employeeUserName">
                        <a href="/update" style="text-decoration: none; color: inherit; cursor: pointer;">
                            <strong><c:out value="${loginUser.userName}" /></strong>님 환영합니다
                        </a>
                    </span>
                    <a href="/logout" class="btn btn-outline btn-sm">로그아웃</a>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="card">
                <div class="tabs">
                    <div class="tab-list">
                        <button type="button" class="tab-button active" data-tab="apply" onclick="switchEmployeeTab('apply')">연차 신청</button>
                        <button type="button" class="tab-button" data-tab="mylist" onclick="switchEmployeeTab('mylist')">내 신청 내역</button>
                    </div>

                    <!-- 연차 신청 탭 -->
                    <div id="applyTab" class="tab-content active">
                        <h2 style="margin-bottom: 0.5rem;">연차 신청하기</h2>
                        <p class="card-description" style="margin-bottom: 1.5rem;">신청한 연차는 관리자의 승인 후 확정됩니다</p>
                        
                        <form id="leaveRequestForm" action="/leave/insert" method="POST" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="leaveType">연차 종류</label>
                                <select id="leaveType" name="leaveType" required>
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
                                    <input type="date" id="startDate" name="startDate" required>
                                </div>
                                <div class="form-group">
                                    <label for="endDate">종료일</label>
                                    <input type="date" id="endDate" name="endDate" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="reason">사유</label>
                                <textarea id="reason" name="reason" rows="4" placeholder="연차 사용 사유를 입력하세요" required></textarea>
                            </div>

                            <div id="fileUploadSection" class="form-group" style="display: none;">
                                <label for="attachment">증빙 서류 첨부 (필수)</label>
                                <input type="file" id="attachment" name="uploadFile" class="form-control">
                                <p class="help-block" style="font-size: 0.8rem; color: #666;">* 병가/경조사는 증빙 서류 업로드가 필요합니다.</p>
                            </div>

                            <button type="button" class="btn btn-primary btn-full" onclick="confirmLeaveRequest(this)">신청하기</button>
                        </form>
                    </div>

                    <!-- 내 신청 내역 탭 -->
                    <div id="mylistTab" class="tab-content">
                        <h2 style="margin-bottom: 0.5rem;">내 연차 신청 내역</h2>
                        <p class="card-description" style="margin-bottom: 1.5rem;">
                            총 <span id="myRequestCount"> ${requestList.size()}</span>건의 신청 내역
                        </p>
                        
                        <div id="myRequestList">
                            <!-- 신청 내역 반복 -->
                            <c:if test="${empty requestList}">
                                <div class="empty-state">신청 내역이 없습니다</div>
                            </c:if>
                            <c:if test="${not empty requestList}">
                                <c:forEach var="request" items="${requestList}">
                                    <div class="request-item" 
                                        data-leave-id="${request.leaveId}"
                                        data-leave-type="${request.leaveType}"
                                        data-start-date="${request.startDate}"
                                        data-end-date="${request.endDate}"
                                        data-reason="${request.reason}"
                                        data-origin-file-name="${request.originFileName}">
                                        <div class="request-header">
                                            <div class="request-info">
                                                <div class="request-title">
                                                    <span style="font-weight: 500;">${request.leaveType}</span>
                                                    <c:set var="statusClass" value="${request.status == 'PENDING' ? 'secondary' : request.status == 'APPROVED' ? 'success' : 'danger'}" />
                                                    <span class="badge badge-${statusClass}">
                                                        ${request.status.description}
                                                    </span>
                                                </div>
                                                <div class="request-date">
                                                    <fmt:formatDate value="${request.startDate}" pattern="yyyy.MM.dd" /> 
                                                    ~ 
                                                    <fmt:formatDate value="${request.endDate}" pattern="yyyy.MM.dd" />
                                                </div>
                                            </div>
                                            <c:if test="${request.status == 'PENDING'}">
                                                <div class="request-actions">
                                                    <button type="button" class="btn btn-outline btn-sm" onclick="openEditModal('${request.leaveId}')">✏️</button>
                                                    <button type="button" class="btn btn-outline btn-sm" onclick="confirmDelete('${request.leaveId}',event)">🗑️</button>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="request-reason">
                                            <span class="request-reason-label">사유: </span><c:out value="${request.reason}" />
                                        </div>
                                        <c:if test="${not empty request.originFileName}">
                                            <div class="request-reason">
                                                <span class="request-reason-label">첨부파일: </span><c:out value="${request.originFileName}" />
                                            </div>
                                        </c:if>
                                        <c:if test="${request.status == 'REJECTED' && not empty request.rejectReason}">
                                            <div class="reject-reason">
                                                <span class="reject-reason-label">반려 사유: </span>
                                                <span class="reject-reason-text">${request.rejectReason}</span>
                                            </div>
                                        </c:if>
                                        <div class="request-created">신청일: <fmt:formatDate value="${request.createdAt}" pattern="yyyy.MM.dd" /></div>
                                        
                                        <form id="deleteForm_${request.leaveId}" action="/leave/delete" method="POST" style="display:none;">
                                            <input type="hidden" name="leaveId" value="${request.leaveId}">
                                        </form>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <!-- 페이징 -->
                            <div class="pagination">
                                <c:if test="${pageMaker.total > 0}">
                                    <div class="pagination-info">
                                        ${pageMaker.cri.pageNum} / ${pageMaker.realEnd} 페이지
                                    </div>
                                    
                                    <div class="pagination-buttons">
                                        <a href="/employee?pageNum=1" class="pagination-btn ${pageMaker.cri.pageNum == 1 ? 'disabled' : ''}">
                                            ⟨⟨
                                        </a>
                                        
                                        <c:if test="${pageMaker.prev}">
                                            <a href="/employee?pageNum=${pageMaker.startPage - 1}" class="pagination-btn">
                                                ⟨
                                            </a>
                                        </c:if>
                                        
                                        <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                                            <a href="/employee?pageNum=${num}" 
                                            class="pagination-btn ${num == pageMaker.cri.pageNum ? 'active' : ''}">
                                                ${num}
                                            </a>
                                        </c:forEach>
                                        
                                        <c:if test="${pageMaker.next}">
                                            <a href="/employee?pageNum=${pageMaker.endPage + 1}" class="pagination-btn">
                                                ⟩
                                            </a>
                                        </c:if>
                                        
                                        <c:set var="realEnd" value="${Math.ceil(pageMaker.total / pageMaker.cri.amount).intValue()}" />
                                        <a href="/employee?pageNum=${realEnd}" class="pagination-btn ${pageMaker.cri.pageNum == realEnd ? 'disabled' : ''}">
                                            ⟩⟩
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="/WEB-INF/jsp/user/edit.jsp" />
        <script>
            window.onload = function() {
                checkServerMessage("${message}", "${errorMsg}");
            };
        </script>
    </div>

</html>