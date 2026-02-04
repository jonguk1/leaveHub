//직원 탭 전환
function switchEmployeeTab(tabName) {
    switchTab(tabName, '#employeeScreen .tabs');
}

//연차 신청 모달 관련
function openLeaveRequestModal() {
    openModal('leaveRequestModal');
}
function closeLeaveRequestModal() {
    closeModal('leaveRequestModal');
    resetForm('leaveRequestForm');
}

// 수정 모달 열기
function openEditModal(leaveId) {
    // JSP에서 data-* 속성으로 저장된 데이터를 가져와서 폼에 채우기
    const requestElement = document.querySelector(`[data-leave-id="${leaveId}"]`);

    if (!requestElement) {
        console.error("해당 연차를 찾을 수 없습니다. ID:", leaveId);
        return;
    }

    const leaveType = requestElement.dataset.leaveType;
    const startDate = requestElement.dataset.startDate;
    const endDate = requestElement.dataset.endDate;
    const reason = requestElement.dataset.reason;

    console.log(leaveType, startDate, endDate, reason);

    document.getElementById('editLeaveId').value = leaveId;
    document.getElementById('editLeaveType').value = leaveType;
    document.getElementById('editStartDate').value = startDate;
    document.getElementById('editEndDate').value = endDate;
    document.getElementById('editReason').value = reason;

    openModal('editModal');
}

function closeEditModal() {
    closeModal('editModal');
    resetForm('editForm');
}

// 삭제 확인
function confirmDelete(requestId) {
    if (confirmAction('정말 삭제하시겠습니까?')) {
        // JSP 폼 제출 또는 삭제 링크 클릭
        const form = document.getElementById('deleteForm_' + requestId);
        if (form) {
            form.submit();
        }
    }
}

document.getElementById('leaveRequestForm').onsubmit = function (e) {
    const start = new Date(document.getElementById('startDate').value);
    const end = new Date(document.getElementById('endDate').value);

    if (start > end) {
        alert("종료일은 시작일보다 빠를 수 없습니다.");
        e.preventDefault(); // 폼 전송 중단
        return false;
    }
};