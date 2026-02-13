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
    const leaveType = document.getElementById("leaveType");
    const fileUploadSection = document.getElementById("fileUploadSection");
    const attachment = document.getElementById("attachment");
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

    // 연차 종류 변경 이벤트 리스너
    leaveType.addEventListener("change", function () {
        const selectedValue = this.value;

        // 병가 또는 경조사일 때만 파일 업로드 칸 표시
        if (selectedValue === "병가" || selectedValue === "경조사") {
            fileUploadSection.style.display = "block";
            attachment.required = true; // 필수 입력으로 변경
        } else {
            fileUploadSection.style.display = "none";
            attachment.required = false; // 필수 해제
            attachment.value = ""; // 선택했던 파일 초기화
        }
    });

    // 유효성 검사
    form.addEventListener("submit", function (e) {
        if ((leaveType.value === "병가" || leaveType.value === "경조사") && !attachment.value) {
            alert("증빙 서류를 첨부해야 합니다.");
            e.preventDefault();
        }
    });
});

//파일 용량 검사 
const attachment = document.getElementById("attachment");
attachment.addEventListener("change", function () {
    const file = this.file[0];
    if (!file) return;

    //용량 체크
    const maxSize = 5 * 1024 * 1024;
    if (file.size > maxSize) {
        alert("파일 용량이 너무 큽니다. 5MB 이하의 파일만 업로드 가능합니다.");
        this.value = ""; // 파일 선택 취소 (비우기)
        return;
    }

    const allowedExtensions = ["jpg", "jpeg", "png", "pdf"];
    const fileExtension = file.name.split(".").pop().toLowerCase();

    if (!allowedExtensions.includes(fileExtension)) {
        alert("허용되지 않는 파일 형식입니다. (jpg, png, pdf만 가능)");
        this.value = ""; // 파일 선택 취소
        return;
    }

});
