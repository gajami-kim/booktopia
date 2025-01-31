document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("#form__wrap");
  const checkAll = document.querySelector(".terms__check__all input");
  const checkBoxes = document.querySelectorAll(".input__check input");
  const submitButton = document.getElementById("submitButton");
  const conPwd = document.getElementById('pwdConfirm');
  const pwd = document.getElementById('pwd');
  const email = document.getElementById('userEmail');
  const inputId = document.getElementById('id');
  const joinCheckIdBtn = document.getElementById('joinCheckIdBtn');
  const userName = document.getElementById('userName');
  const helloNum = document.getElementById('helloNum');

  // Initialize helloNum value
  helloNum.value = '0';

  let elSuccessMessage = document.querySelector('.success-message');
  let elFailureMessageTwo = document.querySelector('.failure-message2');
  let elMismatchMessage = document.querySelector('.mismatch-message');
  let elStrongPasswordMessage = document.querySelector('.strongPassword-message');

  if (!form) console.error("Form element not found");
  if (!checkAll) console.error("Check all element not found");
  if (!submitButton) console.error("Submit button element not found");
  if (!conPwd) console.error("Password confirm element not found");
  if (!pwd) console.error("Password element not found");
  if (!email) console.error("Email element not found");
  if (!inputId) console.error("ID input element not found");
  if (!joinCheckIdBtn) console.error("Join check ID button element not found");

  checkBoxes.forEach((item, index) => {
    if (!item) console.error(`Checkbox element at index ${index} not found`);
  });

  const agreements = {
    termsOfService: false,
    privacyPolicy: false,
    allowPromotions: false
  };

  const idRegex = /^[A-Za-z0-9]{6,13}$/;
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

  function isIdOk(str) {
    return idRegex.test(str);
  }

  function strongPassword(str) {
    return passwordRegex.test(str);
  }

  function isMatch(pwd, conPwd) {
    return pwd === conPwd;
  }

  function toggleSubmitButton() {
    const { termsOfService, privacyPolicy } = agreements;
    const isFormValid = termsOfService
        && privacyPolicy
        && isIdOk(inputId.value)
        && isMatch(pwd.value, conPwd.value)
        && strongPassword(pwd.value)
        && email.value.trim() !== ''
        && userName.value.trim() !== ''
        && helloNum.value === '1';

    joinCheckIdBtn.disabled = !isIdOk(inputId.value);
    submitButton.disabled = !isFormValid;

    if (isFormValid) {
      submitButton.type = 'submit'; // 유효성 검사를 통과하면 submitButton의 type을 'submit'으로 변경
    } else {
      submitButton.type = 'button'; // 유효성 검사를 통과하지 못하면 type을 'button'으로 유지
    }
  }

  toggleSubmitButton();

  checkBoxes.forEach((item) => item.addEventListener("input", toggleCheckbox));

  function toggleCheckbox(e) {
    const { checked, id } = e.target;
    agreements[id] = checked;
    e.target.parentNode.classList.toggle("active");
    checkAllStatus();
    toggleSubmitButton();
  }

  function checkAllStatus() {
    const { termsOfService, privacyPolicy, allowPromotions } = agreements;
    checkAll.checked = termsOfService && privacyPolicy && allowPromotions;
  }

  checkAll.addEventListener("click", (e) => {
    const { checked } = e.target;
    checkBoxes.forEach((item) => {
      item.checked = checked;
      agreements[item.id] = checked;
      if (checked) {
        item.parentNode.classList.add("active");
      } else {
        item.parentNode.classList.remove("active");
      }
    });
    toggleSubmitButton();
  });

  inputId.onkeyup = function () {
    if (inputId.value.length !== 0) {
      if (isIdOk(inputId.value) === false) {
        elSuccessMessage.classList.add('hide');
        elFailureMessageTwo.classList.remove('hide');
      } else if (isIdOk(inputId.value)) {
        elSuccessMessage.classList.remove('hide');
        elFailureMessageTwo.classList.add('hide');
      }
      joinCheckIdBtn.disabled = false;
    } else {
      elSuccessMessage.classList.add('hide');
      elFailureMessageTwo.classList.add('hide');
      joinCheckIdBtn.disabled = true;
    }
    toggleSubmitButton();
  }

  async function checkId(inputIdVal) {
    try {
      const url = '/user/check';
      const config = {
        method: "POST",
        headers: {
          "Content-Type": "text/plain; charset=UTF-8"
        },
        body: inputIdVal
      };
      const resp = await fetch(url, config);
      const result = await resp.text();
      return result;
    } catch (error) {
      console.log(error);
    }
  }

  joinCheckIdBtn.addEventListener('click', () => {
    helloNum.value = '1';
    const inputIdVal = inputId.value;
    checkId(inputIdVal).then(result => {
      if (result === '0') {
        alert("사용 가능한 아이디입니다.");
        joinCheckIdBtn.disabled = true;
        inputId.setAttribute("readonly", true);
      } else if (result === '1') {
        alert("사용 불가능한 아이디입니다.");
        inputId.value = "";
        joinCheckIdBtn.disabled = false;
        inputId.removeAttribute("readonly");
      }
      toggleSubmitButton();
    });
  });

  conPwd.addEventListener('keyup', () => {
    validatePassword();
    toggleSubmitButton();
  });

  function validatePassword() {
    if (pwd.value !== conPwd.value) {
      conPwd.style.color = "red";
    } else {
      conPwd.style.color = "green";
    }
  }

  pwd.onkeyup = function () {
    if (pwd.value.length !== 0) {
      if (strongPassword(pwd.value)) {
        elStrongPasswordMessage.classList.add('hide');
      } else {
        elStrongPasswordMessage.classList.remove('hide');
      }
    } else {
      elStrongPasswordMessage.classList.add('hide');
      toggleSubmitButton();
    }
  };

  conPwd.onkeyup = function () {
    if (conPwd.value.length !== 0) {
      if (isMatch(pwd.value, conPwd.value)) {
        elMismatchMessage.classList.add('hide');
      } else {
        elMismatchMessage.classList.remove('hide');
      }
    } else {
      elMismatchMessage.classList.add('hide');
      toggleSubmitButton();
    }
  };

  userName.onkeyup = function () {
    if (userName.value.length !== 0) {
      toggleSubmitButton();
    } else {
      submitButton.disabled = true;
    }
  }

  email.onkeyup = function() {
    if (email.value.length !== 0) {
      toggleSubmitButton();
    } else {
      submitButton.disabled = true;
    }
  }

});
