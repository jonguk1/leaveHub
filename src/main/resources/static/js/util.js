// 탭 전환
function switchTab(tabName, containerSelector) {
    const container = document.querySelector(containerSelector);
    if (!container) return;

    // 모든 탭 버튼 비활성화
    const buttons = container.querySelectorAll('.tab-button');
    buttons.forEach(btn => btn.classList.remove('active'));

    // 모든 탭 컨텐츠 숨기기
    const contents = container.querySelectorAll('.tab-content');
    contents.forEach(content => content.classList.remove('active'));

    // 클릭한 탭 활성화
    const activeButton = container.querySelector(`[data-tab="${tabName}"]`);
    if (activeButton) {
        activeButton.classList.add('active');
    }

    // 해당 컨텐츠 표시
    const activeContent = document.getElementById(tabName + 'Tab');
    if (activeContent) {
        activeContent.classList.add('active');
    }
}

// 모달 열기
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
    }
}

// 모달 닫기
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
    }
}
// 확인 대화상자
function confirmAction(message) {
    return confirm(message);
}

// 폼 초기화
function resetForm(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
    }
}

//공통 메시지
function checkServerMessage(msg, error) {
    if (msg && msg.trim() !== "") {
        alert(msg);
    }
    if (error && error.trim() !== "") {
        alert(error);
    }
}