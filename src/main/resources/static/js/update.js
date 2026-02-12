document.addEventListener("DOMContentLoaded", function () {
    // 필요한 요소들 가져오기
    const form = document.getElementById("updateForm");
    const currentPassword = document.getElementById("currentPassword"); // 현재비번(인증용)
    const userName = document.getElementById("userName");               // 이름
    const password = document.getElementById("password");               // 새 비밀번호
    const passwordConfirm = document.getElementById("passwordConfirm"); // 새 비밀번호 확인

    const pwMessage = document.getElementById("pwMessage");
    const nameMessage = document.getElementById("nameMessage");
    const submitBtn = document.querySelector('button[type="submit"]');

    /**
     * 비밀번호 규칙 검사 (8자 이상, 영문+숫자)
     */
    function validatePasswordRule(pw) {
        if (pw === "") return true;
        const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        return regex.test(pw);
    }

    /**
     * 버튼 활성화 여부 결정
     */
    function validateFormState() {
        // 필수값 체크
        const isCurrentPwFilled = currentPassword.value.trim() !== "";
        const isNameFilled = userName.value.trim() !== "";

        //새 비밀번호 체크: 입력 시에만 검사
        let isPwValid = true;

        const pwValue = password.value;
        const confirmValue = passwordConfirm.value;

        // 새 비밀번호 칸들 중 하나라도 글자가 입력된 경우
        if (pwValue !== "" || confirmValue !== "") {
            const isMatch = (pwValue === confirmValue);
            const isRuleOk = validatePasswordRule(pwValue);

            if (!isMatch) {
                pwMessage.textContent = "비밀번호가 일치하지 않습니다.";
                pwMessage.style.color = "red";
                pwMessage.style.display = "block";
                isPwValid = false;
            } else if (!isRuleOk) {
                pwMessage.textContent = "새 비밀번호는 8자 이상 영문+숫자 조합이어야 합니다.";
                pwMessage.style.color = "red";
                pwMessage.style.display = "block";
                isPwValid = false;
            } else {
                pwMessage.textContent = "사용 가능한 비밀번호입니다.";
                pwMessage.style.color = "green";
                pwMessage.style.display = "block";
                isPwValid = true;
            }
        } else {
            // 새 비번 칸이 둘 다 비어있으면 메시지 숨김
            pwMessage.style.display = "none";
            isPwValid = true;
        }

        //버튼 상태 업데이트
        submitBtn.disabled = !(isCurrentPwFilled && isNameFilled && isPwValid);
    }

    //실시간 감시
    [currentPassword, userName, password, passwordConfirm].forEach(el => {
        el.addEventListener("input", validateFormState);
    });

    //초기 상태 한 번 체크
    validateFormState();

    /**
     * 최종체크
     */
    form.addEventListener("submit", function (e) {
        if (currentPassword.value.trim() === "") {
            alert("정보 수정을 위해 현재 비밀번호를 입력해주세요.");
            currentPassword.focus();
            e.preventDefault();
            return;
        }

        if (userName.value.trim() === "") {
            alert("이름은 필수 입력 항목입니다.");
            userName.focus();
            e.preventDefault();
            return;
        }
    });
});

/**
 * 회원 탈퇴 확인
 */
function confirmDelete(event) {
    if (confirm('정말 탈퇴하시겠습니까?\n탈퇴 시 즉시 로그아웃됩니다.')) {
        const form = document.getElementById('deleteForm');
        if (form) {
            const btn = event.currentTarget;
            if (btn) {
                btn.disabled = true;
                btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> 처리 중...';
            }
            form.submit();
        } else {
            alert("삭제 폼을 찾을 수 없습니다.");
        }
    }
}