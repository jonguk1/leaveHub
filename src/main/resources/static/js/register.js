document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("registerForm");
    const userId = document.getElementById("userId");
    const userName = document.getElementById("userName");
    const password = document.getElementById("password");
    const passwordConfirm = document.getElementById("passwordConfirm");
    const pwMessage = document.getElementById("pwMessage");
    const idMessage = document.getElementById("idMessage");
    const nameMessage = document.getElementById("nameMessage");
    const submitBtn = document.querySelector('button[type="submit"]');

    document.getElementById('checkIdBtn').onclick = function (e) {
        if (userId.value.trim() === "") {
            idMessage.textContent = "아이디를 입력해주세요."
            idMessage.style.display = "block";
            userId.focus();
            e.preventDefault();
            return;
        }

        // 서버로 중복 체크 요청
        fetch('/checkId?userId=' + userId.value)
            .then(response => response.json())
            .then(data => {
                idMessage.style.display = "block";
                if (data > 0) { // 1이면 중복
                    idMessage.innerText = "이미 사용 중인 아이디입니다.";
                    idMessage.style.color = "red";
                    submitBtn.disabled = true; // 다시 막기
                    submitBtn.style.opacity = "0.5"; // 흐리게 처리
                } else { // 0이면 사용 가능
                    idMessage.innerText = "사용 가능한 아이디입니다.";
                    idMessage.style.color = "green";
                    submitBtn.disabled = false; // 버튼 비활성화 해제!
                    submitBtn.style.opacity = "1"; // 시각적으로도 밝게
                }
            })
            .catch(error => console.error('Error:', error));
    };


    /**
     * 비밀번호 규칙 검사 (8자 이상, 영문+숫자)
     */
    function validatePasswordRule(pw) {
        const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        return regex.test(pw);
    }

    /**
     * 비밀번호 일치체크
     */
    function checkPasswordMatch() {
        if (passwordConfirm.value === "") {
            pwMessage.style.display = "none";
            passwordConfirm.setCustomValidity("");
            return;
        }

        if (password.value !== passwordConfirm.value) {
            pwMessage.textContent = "비밀번호가 일치하지 않습니다.";
            pwMessage.style.display = "block";
            passwordConfirm.setCustomValidity("mismatch");
        } else {
            pwMessage.textContent = "비밀번호가 일치합니다.";
            pwMessage.style.color = "green";
            pwMessage.style.display = "block";
            passwordConfirm.setCustomValidity("");
        }
    }

    password.addEventListener("input", checkPasswordMatch);
    passwordConfirm.addEventListener("input", checkPasswordMatch);

    /**
     * 최종체크
     */
    form.addEventListener("submit", function (e) {

        console.log("이벤트 객체 확인:", e);

        // 아이디 공백 체크
        if (userId.value.trim() === "") {
            idMessage.textContent = "아이디를 입력해주세요."
            idMessage.style.display = "block";
            userId.focus();
            e.preventDefault();
            return;
        }
        //이름 공백 체크
        if (userName.value.trim() === "") {
            nameMessage.textContent = "이름을 입력해주세요."
            nameMessage.style.display = "block";
            userName.focus();
            e.preventDefault();
            return;
        }

        // 비밀번호 규칙 체크
        if (!validatePasswordRule(password.value)) {
            pwMessage.textContent = "비밀번호는 8자 이상이며 영문과 숫자를 포함해야 합니다."
            pwMessage.style.display = "block";
            password.focus();
            e.preventDefault();
            return;
        }

        // 비밀번호 일치 체크
        if (password.value !== passwordConfirm.value) {
            pwMessage.textContent = "비밀번호가 일치하지 않습니다."
            pwMessage.style.display = "block";
            passwordConfirm.focus();
            e.preventDefault();
            return;
        }

    });


});

