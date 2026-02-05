// ========== 관리자 기능 ==========

// 관리자 탭 전환
function switchAdminTab(tabName) {
    switchTab(tabName, '#adminScreen .tabs');
}

// 승인 확인
function confirmApprove(leaveId) {
    if (confirmAction('이 신청을 승인하시겠습니까?')) {
        const form = document.getElementById('approveForm_' + leaveId);
        if (form) {
            form.submit();
        }
    }
}

// 반려 모달 열기
function openRejectModal(leaveId, leaveType, startDate, endDate) {
    document.getElementById('leaveId').value = leaveId;

    // 신청 정보 표시
    document.getElementById('rejectRequestInfo').innerHTML = `
        <div><span class="info-label">직원: </span><span class="info-value">${loginUser.userName}</span></div>
        <div><span class="info-label">연차 종류: </span><span class="info-value">${leaveType}</span></div>
        <div><span class="info-label">기간: </span><span>${startDate} ~ ${endDate}</span></div>
    `;

    openModal('rejectModal');
}

// 반려 모달 닫기
function closeRejectModal() {
    closeModal('rejectModal');
    resetForm('rejectForm');
}
