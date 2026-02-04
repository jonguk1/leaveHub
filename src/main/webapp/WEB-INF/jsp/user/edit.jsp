<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<body>
    <!-- Edit Modal -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">연차 신청 수정</h3>
                <p class="modal-description">대기 중인 연차 신청을 수정할 수 있습니다</p>
            </div>
            <form id="editForm" action="/leave/update" method="POST">
                <div class="form-group">
                    <label for="editLeaveType">연차 종류</label>
                    <select id="editLeaveType" name="leaveType" required>
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
                        <label for="editStartDate">시작일</label>
                        <input type="date" id="editStartDate" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label for="editEndDate">종료일</label>
                        <input type="date" id="editEndDate" name="endDate" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="editReason">사유</label>
                    <textarea id="editReason" name="reason" rows="4" required></textarea>
                </div>

                <div class="modal-footer">
                    <input type="hidden" id="editLeaveId" name="leaveId">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">수정 완료</button>
                    <button type="button" class="btn btn-outline" onclick="closeEditModal()">취소</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>