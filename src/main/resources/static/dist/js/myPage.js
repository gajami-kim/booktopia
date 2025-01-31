document.addEventListener('DOMContentLoaded', () => {
    const idElement = document.getElementById("realId");
    const modifyInfo = document.getElementById("modifyMyInfo");
    const moveContainer = document.getElementById("myPageInfoRightWrapId");
    const moveContainerUserDelete = document.getElementById("deleteMemberType");
    const comDelete = document.getElementById("comUser");
    const subinfodiv = document.getElementById("myPageSubWrap");
    const today = new Date();

    if (!idElement || !modifyInfo || !moveContainer || !moveContainerUserDelete || !subinfodiv) {
            console.log("idElement:", idElement);
            console.log("modifyInfo:", modifyInfo);
            console.log("moveContainer:", moveContainer);
            console.log("moveContainerUserDelete:", moveContainerUserDelete);
            console.log("subinfodiv:", subinfodiv);
            console.error("필요한 요소 중 하나 이상이 DOM에 없습니다.");
            return;
        }

    const idVal = idElement.innerText;
    const myPageCoupon = "/mypage/couponlist";
    const myPageModify = "/mypage/modify";
    const myPageAddress = "/mypage/changeaddr";
    const myPagePayment = "/mypage/payinfo";
    const isSocial = "/user/deleteUser";
    const myPageinquiry = "/mypage/qnaresult";

    // 유저 타입 확인 후 페이지 호출
    isSocialUser(idVal).then(result => {
        if (result !== "일반") {
            pageCall(isSocial, moveContainerUserDelete);
            comDelete.style.display = "none";
            modifyInfo.style.display = "none";
        }
    });



    pageCall(myPagePayment, moveContainer);

      usingsub(idVal).then(result => {
          if (result.length === 0) {
              subinfodiv.innerHTML = `
              <div class="myPageSubInfo">
                  이용중인 구독권이 없습니다.
              </div>
              <div class="myPageSubMoveLink">
                 <a class="myPageSubInfoSpan" href="/subscribe/info">구독권 보러가기</a>
              </div>`;
          } else {
              var originalString = result.itemName;
              var newString = originalString.match(/\d+/)[0]; // 1, 3, 6
              var today = new Date(); // 현재 날짜
              var approvedAt = new Date(result.approvedAt); // 결제 날짜

              // 구독 기간 계산
              switch (newString) {
                  case "1":
                      approvedAt.setMonth(approvedAt.getMonth() + 1);
                      break;
                  case "3":
                      approvedAt.setMonth(approvedAt.getMonth() + 3);
                      break;
                  case "6":
                      approvedAt.setMonth(approvedAt.getMonth() + 6);
                      break;
              }

              // 날짜 비교
              if (today > approvedAt) {
                  subinfodiv.innerHTML = `
                  <div class="myPageSubInfo">
                      이용중인 구독권이 없습니다.
                  </div>
                  <div class="myPageSubMoveLink">
                       <a class="myPageSubInfoSpan"  href="/subscribe/info">구독권 보러가기</a>
                  </div>`;
              } else {
                  // 구독 중인 경우에 대한 처리
                  subinfodiv.innerHTML =`<div class="myPageSubInfo">
                      현재 구독 중입니다. <br>
                  </div>
                     <span class="myPageSubInfoSpan"> 만료일: ${approvedAt.toLocaleDateString('ko-KR')}</span>`;
              }
          }
      });

    // 버튼 클릭 이벤트 설정
    document.querySelectorAll('#myPageCoupon, #myPageModify, #myPageAddress, #myPagePayment, #myPageQna').forEach(button => {
        button.addEventListener('click', (e) => {
            const myPageMoveBtn = e.target.id;
            moveContainer.innerHTML = "";

            switch (myPageMoveBtn) {
                case 'myPageCoupon':
                    pageCall(myPageCoupon, moveContainer);
                    break;
                case 'myPageModify':
                    pageCall(myPageModify, moveContainer);
                    break;
                case 'myPageAddress':
                            pageCall(myPageAddress, moveContainer);
                    break;
                case 'myPagePayment':
                    pageCall(myPagePayment, moveContainer);
                    break;
                case 'myPageQna' :
                    pageCall(myPageinquiry, moveContainer);
            }
        });
    });

    document.getElementById("comUserDelete").addEventListener("click", () => {
        const isRealDelete = button_event();
        if (isRealDelete) {
            deleteComUser(idVal).then(result => {
                if (result == 1) {
                    logout();
                    alert("회원탈퇴 하셨습니다.");
                } else if (result == 0) {
                    alert("회원탈퇴 실패 ");
                }
            });
        }
    });
});

function button_event(){
  return confirm("정말 탈퇴 하시겠습니까??") == true ? true : false;
}

function logout() {
    // 로그아웃 요청을 서버로 보냄
    fetch('/user/logout')
    .then(response => {
        if (response.ok) {
            window.location.href = '/login'; // 로그아웃 후 로그인 페이지로 리디렉션
        } else {
            alert('로그아웃에 실패했습니다.');
        }
    })
    .catch(error => {
        alert('로그아웃 중 오류가 발생했습니다.');
    });
}

// 페이지 호출 함수
function pageCall(link, callBox) {
    const request = new XMLHttpRequest();
    request.open("GET", link, true);
    request.send();
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                if (callBox) {
                    callBox.innerHTML = request.responseText;
                    // 스크립트 파일을 조건에 따라 추가
                    switch (link) {
                        case '/mypage/modify':
                            loadScript('/dist/js/myPageModify.js');
                            break;
                        case '/mypage/changeaddr':
                            loadScript('/dist/js/changeaddr.js');
                            break;
                        case '/mypage/couponlist':
                            loadScript('/dist/js/couponlist.js');
                            break;
                        case '/mypage/payinfo':
                            loadScript('/dist/js/payinfo.js');
                            break;
                        case '/mypage/qnaresult':
                            loadScript('/dist/js/oneInquiry.js')
                    }
                }
            } else {
                console.error("요청 실패, 상태 코드: " + request.status);
            }
        }
    }
}

// 스크립트가 이미 포함되어 있는지 확인하고 추가하는 함수
function loadScript(src) {
    removeAllScriptsExcept(src)
    if (!isScriptAlreadyIncluded(src)) {
        const script = document.createElement('script');
        script.src = src;
        document.body.appendChild(script);
    }
}

// 스크립트가 이미 포함되어 있는지 확인하는 함수
function isScriptAlreadyIncluded(src) {
    const scripts = document.getElementsByTagName('script');
    for (let i = 0; i < scripts.length; i++) {
        if (scripts[i].src.includes(src)) {
            return true;
        }
    }
    return false;
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더합니다.
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 특정 스크립트들을 제외하고 모든 스크립트를 제거하는 함수
function removeAllScriptsExcept(dynamicSrc) {
    const srcToKeep = [
        "/dist/js/myPage.js",
        "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js",
        "/dist/js/header.js",
        dynamicSrc // 동적으로 들어오는 src 값을 추가
    ];

    const scripts = document.getElementsByTagName('script');
    for (let i = scripts.length - 1; i >= 0; i--) { // script 태그를 달고있는 전체 태그 개수
        let keepScript = false;
        for (let j = 0; j < srcToKeep.length; j++) { // script 태그를 달고있는 필수요소의 개수만큼
            if (scripts[i].src.includes(srcToKeep[j])) { // json 형식에 있는 js 링크들
                keepScript = true;
                if(srcToKeep[j]== '/dist/js/payinfo.js' || '/dist/js/couponlist.js' || "/dist/js/oneInquiry.js" ){
                    keepScript = false;
                }
                break;
            }
        }
        if (!keepScript) {
            scripts[i].parentNode.removeChild(scripts[i]);
        }
    }
}

// 유저 타입을 확인하는 비동기 함수
async function isSocialUser(id) {
    try {
        const resp = await fetch("/user/isSocialUser/" + id);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function deleteComUser(id){
    const url = "/user/deleteMyPageUser/"+id;
    const resp = await fetch(url);
    const result = await resp.text();
    return result;
}

async function usingsub(id){
    try {
            const response = await fetch("/user/usingsub/"+id);
            if (!response.ok) {
                throw new Error("HTTP error, status = " + response.status);
            }
            const result = await response.json();
            return result;
        } catch (error) {
            console.error("Error fetching data:", error);
            return null; // 오류 발생 시 null 반환 또는 적절한 오류 처리 수행
        }

}

