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
    const originFileName = requestElement.dataset.originFileName;

    if (leaveType == '병가' || leaveType == '경조사') {
        document.getElementById("editFileGroup").style.display = "block";
    } else {
        document.getElementById("editFileGroup").style.display = "none";
    }


    document.getElementById('editLeaveId').value = leaveId;
    document.getElementById('editLeaveType').value = leaveType;
    document.getElementById('editStartDate').value = startDate;
    document.getElementById('editEndDate').value = endDate;
    document.getElementById('editReason').value = reason;
    const fileInfoArea = document.getElementById('editFileInfo');
    if (originFileName) {
        fileInfoArea.innerHTML = `현재 첨부된 파일: ${originFileName}`;
    } else {
        fileInfoArea.innerHTML = '첨부된 파일이 없습니다.';
    }

    openModal('editModal');
}

function closeEditModal() {
    closeModal('editModal');
    resetForm('editForm');
}

// 삭제 확인
function confirmDelete(leaveId, event) {
    if (!confirm('정말 삭제하시겠습니까?')) return;

    const formId = 'deleteForm_' + leaveId;

    console.log("삭제 폼 ID:", formId); // 디버깅용 로그

    submitFormWithDisable({
        formId,
        submitBtn: event.currentTarget,
        loadingText: '삭제 중...'
    });
}

/**
 *  유효성 검사(신청용)
 */
function validateLeaveForm() {
    const leaveType = document.getElementById("leaveType");
    const attachment = document.getElementById("attachment");
    const startVal = document.getElementById('startDate').value;
    const endVal = document.getElementById('endDate').value;

    if (startVal && endVal) {
        if (new Date(startVal) > new Date(endVal)) {
            alert("종료일은 시작일보다 빠를 수 없습니다.");
            return false;
        }
    }

    const isSpecialLeave = leaveType && (leaveType.value === "병가" || leaveType.value === "경조사");
    if (isSpecialLeave && !attachment.value) {
        alert("증빙 서류를 첨부해야 합니다.");
        return false;
    }
    return true;
}

/**
 * 2.  유효성 검사 (수정용)
 */
function validateEditForm() {
    const leaveType = document.getElementById("editLeaveType");
    const attachment = document.getElementById("editAttachment");
    const startVal = document.getElementById('editStartDate').value;
    const endVal = document.getElementById('editEndDate').value;
    // 기존 파일 존재 여부 확인 (텍스트 영역 등에서 판단)
    const currentFile = document.getElementById('editFileInfo').innerText;
    const hasExistingFile = currentFile.includes('현재 첨부된 파일:');

    if (startVal && endVal) {
        if (new Date(startVal) > new Date(endVal)) {
            alert("종료일은 시작일보다 빠를 수 없습니다.");
            return false;
        }
    }

    const isSpecialLeave = leaveType && (leaveType.value === "병가" || leaveType.value === "경조사");
    // 기존 파일도 없고, 새로 선택한 파일도 없는 경우만 체크
    if (isSpecialLeave && !hasExistingFile && !attachment.value) {
        alert("증빙 서류를 첨부해야 합니다.");
        return false;
    }
    return true;
}

// 신청 확인
function confirmLeaveRequest(btn) {
    if (!validateLeaveForm()) return;
    if (!confirm('연차를 신청하시겠습니까?')) return;

    submitFormWithDisable({
        formId: 'leaveRequestForm',
        submitBtn: btn,
        loadingText: '신청 중...'
    });
}

// 수정 확인
function confirmEditLeaveRequest(btn) {
    if (!validateEditForm()) return;
    if (!confirm('수정 내용을 저장하시겠습니까?')) return;

    submitFormWithDisable({
        formId: 'editForm', // 수정 폼 ID
        submitBtn: btn,
        loadingText: '수정 중...'
    });
}



