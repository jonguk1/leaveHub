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
function confirmDelete(leaveId, event) {
    if (confirm('정말 삭제하시겠습니까?')) {
        const form = document.getElementById('deleteForm_' + leaveId);

        if (form) {
            // 중복 클릭 방지
            const btn = event.currentTarget;
            if (btn) {
                // 버튼 비활성화
                btn.disabled = true;
                btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> 처리 중...';
            }
            // 폼 제출
            form.submit();
        } else {
            alert("삭제 대상을 찾을 수 없습니다.");
        }
    }
}

// 연차 신청 및 수정 폼 유효성 검사
document.addEventListener('DOMContentLoaded', function () {
    const leaveForm = document.getElementById('leaveRequestForm');
    const editForm = document.getElementById('editForm');
    // 신청 폼 유효성 검사
    if (leaveForm) {
        leaveForm.onsubmit = function (e) {
            const startVal = document.getElementById('startDate').value;
            const endVal = document.getElementById('endDate').value;

            if (!startVal || !endVal) return true; // HTML5 required가 처리하도록 함

            const start = new Date(startVal);
            const end = new Date(endVal);

            if (start > end) {
                alert("종료일은 시작일보다 빠를 수 없습니다.");
                e.preventDefault();
                return false;
            }
        };
    }

    // 수정 폼 유효성 검사
    if (editForm) {
        editForm.onsubmit = function (e) {
            const startVal = document.getElementById('editStartDate').value;
            const endVal = document.getElementById('editEndDate').value;

            if (!startVal || !endVal) return true; // HTML5 required가 처리하도록 함

            const start = new Date(startVal);
            const end = new Date(endVal);

            if (start > end) {
                alert("종료일은 시작일보다 빠를 수 없습니다.");
                e.preventDefault();
                return false;
            }
        };
    }
});