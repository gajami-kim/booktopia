var changePwd = document.getElementById("changePwd");
var changeNewPwd = document.getElementById("changeNewPwd");
var submitButton = document.getElementById("submitButton");
var phone = document.getElementById("modifyPhone");

var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
var phoneRegex = /^010[0-9]{8}$/;
var modifyid = document.getElementById("realId");

 callinfo(modifyid.innerText).then(result => {
            phone.value = result.phone;
        });


// 비밀번호 유효성 검사 함수
function validatePassword() {
    if (changePwd.value !== changeNewPwd.value) {
        changeNewPwd.style.color = "red";
    } else {
        changeNewPwd.style.color = "green";
    }
    toggleSubmitButton();
}

// 비밀번호 강력성 검사 함수
function strongPassword(str) {
    return passwordRegex.test(str);
}

// 전화번호 유효성 검사 함수
function strongPhone(str) {
    return phoneRegex.test(str) || str.length == 0;
}

// 제출 버튼 활성/비활성화 함수
function toggleSubmitButton() {
    const arePasswordsEmpty = changePwd.value.length === 0 && changeNewPwd.value.length === 0; //
    const isPasswordValid = strongPassword(changePwd.value) && changePwd.value === changeNewPwd.value;
    const isPhoneValid = strongPhone(phone.value);

    // 비밀번호가 비어 있고 전화번호가 유효한 경우, 혹은 비밀번호가 유효하고 전화번호가 유효한 경우
    submitButton.disabled = !(isPhoneValid && (arePasswordsEmpty || isPasswordValid));
}

function button_event(){
  if (confirm("정말 수정하시겠습니까??") == true){    //확인
       document.getElementById('myinfoModifyForm').submit();
       alert("수정 완료 되었습니다.");
  }else{   //취소
      return;
  }
}

async function callinfo(id) {
            const response = await fetch("/user/callinfo/" + id);
            const result = await response.json();
            return result;
        }

// 입력 필드에 keyup 이벤트 리스너 추가
changePwd.addEventListener("keyup", () => {
    toggleSubmitButton();
});

changeNewPwd.addEventListener("keyup", () => {
    validatePassword();
});

phone.addEventListener("keyup", () => {
    toggleSubmitButton();
});
