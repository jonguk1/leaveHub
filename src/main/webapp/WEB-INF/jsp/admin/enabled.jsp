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
                    <span id="adminUserName">                        
                        <a href="/update" style="text-decoration: none; color: inherit; cursor: pointer;">
                            <strong><c:out value="${loginUser.userName}" /></strong>님 환영합니다
                        </a>
                    </span>
                    <a href="/logout" class="btn btn-outline btn-sm">로그아웃</a>
                </div>
            </div>
        </div>
        <div id="adminMainWrapper" style="display: flex;">
            <aside class="admin-sidebar">
                <nav class="sidebar-nav">
                    <a href="/admin" style="text-decoration: none;">
                        <button type="button" class="sidebar-item">
                            <span>연차 신청 관리</span>
                        </button>
                    </a>
                    <a href="/admin/enabledUser" style="text-decoration: none;">
                        <button type="button" class="sidebar-item">
                            <span> 회원가입 승인</span>
                        </button>
                    </a>
                </nav>
            </aside>
            <div class="main-content" style="flex: 1; padding: 30px;">
                <div class="container">
                    <div class="card">
                        <h2 style="margin-bottom: 0.5rem;">전체 회원가입 신청 내역</h2>
                        <p class="card-description" style="margin-bottom: 1.5rem;">직원들의 가입 신청을 승인할 수 있습니다</p>

                        <div class="tabs">
                            <div class="tab-list">
                                    <button type="button" class="tab-button">
                                        전체 (<span>${total}</span>)
                                    </button>
                            </div>

                            <div id="adminRequestList">
                                <div id="adminUserApprovalList">
                                    <c:if test="${empty userList}">
                                        <div class="empty-state">가입 승인 대기 중인 회원이 없습니다.</div>
                                    </c:if>
                                    <c:if test="${not empty userList}">
                                        <c:forEach var="user" items="${userList}">
                                            <div class="request-item">
                                                <div class="request-header">
                                                    <div class="request-info">
                                                        <div class="request-title">
                                                            <span class="request-user">이름: <strong>${user.userName}</strong></span>
                                                            <span class="badge badge-secondary">승인대기</span>
                                                        </div>
                                                    </div>

                                                    <div class="request-actions">
                                                        <button type="button" class="btn btn-success btn-sm" 
                                                                onclick="approveUser('${user.userId}')">승인</button>
                                                        <button type="button" class="btn btn-danger btn-sm" 
                                                                onclick="rejectUser('${user.userId}')">✕ 거절</button>
                                                    </div>

                                                    <form id="approveForm_${user.userId}" action="/admin/approveUser" method="POST" style="display:none;">
                                                        <input type="hidden" name="userId" value="${user.userId}">
                                                    </form>
                                                    <form id="rejectForm_${user.userId}" action="/admin/rejectUser" method="POST" style="display:none;">
                                                        <input type="hidden" name="userId" value="${user.userId}">
                                                        <input type="hidden" name="userName" value="${user.userName}">
                                                    </form>
                                                </div>
                                                <div class="request-created">가입 신청일: ${request.createdAt}</div>
                                            </div>
                                        </c:forEach>
                                    </c:if>

                                    <div class="pagination">
                                        <c:if test="${pageMaker.total > 0}">
                                            <div class="pagination-info">
                                                현재 페이지: <strong>${pageMaker.cri.pageNum}</strong>/
                                                전체 <strong>${pageMaker.total}</strong>건
                                            </div>
                                            
                                            <div class="pagination-buttons">
                                                <a href="/admin/enabledUser?pageNum=1&status=${currentStatus}" 
                                                class="pagination-btn ${pageMaker.cri.pageNum == 1 ? 'disabled' : ''}" title="처음으로">
                                                    ⟨⟨
                                                </a>
                                                
                                                <c:if test="${pageMaker.prev}">
                                                    <a href="/admin/enabledUser?pageNum=${pageMaker.startPage - 1}&status=${currentStatus}" class="pagination-btn">
                                                        ⟨
                                                    </a>
                                                </c:if>
                                                
                                                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                                                    <a href="/admin/enabledUser?pageNum=${num}&status=${currentStatus}" 
                                                    class="pagination-btn ${num == pageMaker.cri.pageNum ? 'active' : ''}">
                                                        ${num}
                                                    </a>
                                                </c:forEach>
                                                
                                                <c:if test="${pageMaker.next}">
                                                    <a href="/admin/enabledUser?pageNum=${pageMaker.endPage + 1}&status=${currentStatus}" class="pagination-btn">
                                                        ⟩
                                                    </a>
                                                </c:if>
                                                
                                                <a href="/admin/enabledUser?pageNum=${pageMaker.realEnd}&status=${currentStatus}" 
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
        </div>
    </div>
    <c:import url="/WEB-INF/jsp/admin/reject.jsp" />
    <script>
        window.onload = function() {
        checkServerMessage("${message}", "${errorMsg}");
        };
    </script>

</html>