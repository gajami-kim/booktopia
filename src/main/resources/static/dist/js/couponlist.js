// 사용자 ID를 가져와 변수에 저장
var couId = document.getElementById("realId");
var idVal = couId.innerText;

// 쿠폰 정보를 가져와 처리하는 함수 호출
callCouponInfo(idVal).then(result => {

    // 결과를 표시할 ul 요소를 가져옴
    const ul = document.getElementById("couponresult");
    ul.innerHTML = ""; // ul 내용 초기화

    // 결과가 없거나 null인 경우 처리
    if(result.length == 0) {
        ul.innerHTML = `<div class="nocouponinfo">쿠폰 내역이 없습니다.</div>`;
    } else {
        // 결과가 있는 경우 각 항목을 ul에 추가
        result.forEach(item => {
            // 새로운 리스트 아이템 생성
            const li = document.createElement("li");
            let couponInfo =`${item.adCouInfo}`;
            let couponPercent = couponInfo.substring(couponInfo.indexOf("%")-2,couponInfo.indexOf("%")+1);
            // 각 필드 값을 리스트 아이템에 추가
            li.innerHTML = `
                <div class="circle"></div>
                <div class="couponWrap">
                    <div class="couponInName">${item.adCouName}</div>
                    <div class="couponInfoLeft">
                        <div id="couponInfoTitle">${couponPercent}</div>
                        <div id="couponInfoGarrent">${item.adCouInfo}</div>
                    </div>
                    <div class="couponInfoRight">
                        ${item.couUse === "Y"
                            ? '<div id="couponInfoAt">사용완료</div>'
                            : `<div id="couponInfoAt">${item.adCouPeriod}</div>`}
                    </div>
                </div>`;
            // ul에 리스트 아이템 추가
            ul.appendChild(li);
        });
    }
});

// 쿠폰 정보를 가져오는 비동기 함수
async function callCouponInfo(id) {
    try {
        // 서버에 요청 보내기
        const response = await fetch("/user/myPageCouponInfo/" + id);
        if (!response.ok) {
            throw new Error("HTTP error, status = " + response.status);
        }
        // JSON 형식으로 응답 받기
        const result = await response.json();
        return result;
    } catch (error) {
        console.error("Error fetching data:", error);
        return null; // 오류 발생 시 null 반환 또는 적절한 오류 처리 수행
    }
}
