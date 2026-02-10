<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<script src="<c:url value='/js/admin.js' />"></script>
<c:import url="/WEB-INF/jsp/common/head.jsp" />

<div id="adminScreen">
        <div class="header">
            <div class="header-content">
                <div class="header-title">
                    <span>📅</span>
                    <span>연차 관리 시스템 (관리자)</span>
                </div>
                <div class="header-user">
                    <span id="adminUserName"><strong><c:out value="${loginUser.userName}" /></strong>님 환영합니다</span>
                    <a href="/logout" class="btn btn-outline btn-sm">로그아웃</a>
                </div>
            </div>
        </div>

        <div class="container">
            <!-- 통계 -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-title">전체 신청</div>
                    <div class="stat-value" id="statTotal"> ${countAll}</div>
                </div>
                <div class="stat-card">
                    <div class="stat-title">대기 중</div>
                    <div class="stat-value yellow" id="statPending">${countPending}</div>
                </div>
                <div class="stat-card">
                    <div class="stat-title">승인됨</div>
                    <div class="stat-value green" id="statApproved">${countApproved}</div>
                </div>
                <div class="stat-card">
                    <div class="stat-title">반려됨</div>
                    <div class="stat-value red" id="statRejected">${countRejected}</div>
                </div>
            </div>

            <div class="card">
                <h2 style="margin-bottom: 0.5rem;">전체 연차 신청 내역</h2>
                <p class="card-description" style="margin-bottom: 1.5rem;">직원들의 연차 신청을 승인 또는 반려할 수 있습니다</p>

                <div class="tabs">
                    <div class="tab-list">
                        <a href="${pageContext.request.contextPath}/admin">
                            <button type="button"
                                class="tab-button ${currentStatus == 'ALL' ? 'active' : ''}">
                                전체 (<span>${countAll}</span>)
                            </button>
                        </a>

                        <a href="${pageContext.request.contextPath}/admin?status=PENDING">
                            <button type="button"
                                class="tab-button ${currentStatus == 'PENDING' ? 'active' : ''}">
                                대기 (<span>${countPending}</span>)
                            </button>
                        </a>

                        <a href="${pageContext.request.contextPath}/admin?status=APPROVED">
                            <button type="button"
                                class="tab-button ${currentStatus == 'APPROVED' ? 'active' : ''}">
                                승인 (<span>${countApproved}</span>)
                            </button>
                        </a>

                        <a href="${pageContext.request.contextPath}/admin?status=REJECTED">
                            <button type="button"
                                class="tab-button ${currentStatus == 'REJECTED' ? 'active' : ''}">
                                반려 (<span>${countRejected}</span>)
                            </button>
                        </a>
                    </div>

                    <div id="adminRequestList">
                        <c:if test="${empty leaveRequestList}">
                            <div class="empty-state">신청 내역이 없습니다</div>
                        </c:if>
                        <c:if test="${not empty leaveRequestList}">
                            <c:forEach var="request" items="${leaveRequestList}">
                                <div class="request-item">
                                    <div class="request-header">
                                        <div class="request-info">
                                            <div class="request-title">
                                                <span class="request-user">직원: ${request.userVO.userName}</span>
                                                <span style="font-weight: 500;"><c:out value="${request.leaveType}" /></span>
                                                <c:set var="statusClass" value="${request.status == 'PENDING' ? 'secondary' : request.status == 'APPROVED' ? 'success' : 'danger'}" />
                                                <span class="badge badge-${statusClass}">
                                                    ${request.status.description}
                                                </span>
                                            </div>
                                            <div class="request-date"><c:out value="${request.startDate}" /> ~ <c:out value="${request.endDate}" /></div>
                                        </div>
                                        <c:if test="${request.status == 'PENDING'}">
                                            <div class="request-actions">
                                                <button type="button" class="btn btn-success btn-sm" 
                                                        onclick="confirmApprove('${request.leaveId}')">✓ 승인</button>
                                                <button type="button" class="btn btn-danger btn-sm" 
                                                        onclick="openRejectModal('${request.leaveId}', '${request.userVO.userName}', '${request.leaveType}', '${request.startDate}', '${request.endDate}')">✕ 반려</button>
                                            </div>
                                            <form id="approveForm_${request.leaveId}" action="/admin/approve" method="POST" style="display:none;">
                                                <input type="hidden" name="leaveId" value="${request.leaveId}">
                                            </form>
                                        </c:if>
                                    </div>
                                    <div class="request-reason">
                                        <span class="request-reason-label">사유: </span><c:out value="${request.reason}" />
                                    </div>
                                    <c:if test="${request.status == 'REJECTED' && not empty request.rejectReason}">
                                        <div class="reject-reason">
                                            <span class="reject-reason-label">반려 사유: </span>
                                            <span class="reject-reason-text"><c:out value="${request.rejectReason}" /></span>
                                        </div>
                                    </c:if>
                                    <div class="request-created">신청일: ${request.createdAt}</div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <div class="pagination">
                            <c:if test="${pageMaker.total > 0}">
                                <div class="pagination-info">
                                    현재 페이지: <strong>${pageMaker.cri.pageNum}</strong> / 전체 데이터: <strong>${pageMaker.total}</strong>건
                                </div>
                                
                                <div class="pagination-buttons">
                                    <a href="/admin?pageNum=1&status=${currentStatus}" 
                                    class="pagination-btn ${pageMaker.cri.pageNum == 1 ? 'disabled' : ''}" title="처음으로">
                                        ⟨⟨
                                    </a>
                                    
                                    <c:if test="${pageMaker.prev}">
                                        <a href="/admin?pageNum=${pageMaker.startPage - 1}&status=${currentStatus}" class="pagination-btn">
                                            ⟨
                                        </a>
                                    </c:if>
                                    
                                    <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                                        <a href="/admin?pageNum=${num}&status=${currentStatus}" 
                                        class="pagination-btn ${num == pageMaker.cri.pageNum ? 'active' : ''}">
                                            ${num}
                                        </a>
                                    </c:forEach>
                                    
                                    <c:if test="${pageMaker.next}">
                                        <a href="/admin?pageNum=${pageMaker.endPage + 1}&status=${currentStatus}" class="pagination-btn">
                                            ⟩
                                        </a>
                                    </c:if>
                                    
                                    <a href="/admin?pageNum=${pageMaker.realEnd}&status=${currentStatus}" 
                                        class="pagination-btn ${pageMaker.cri.pageNum == pageMaker.realEnd ? 'disabled' : ''}">
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
    <c:import url="/WEB-INF/jsp/admin/reject.jsp" />

</html>