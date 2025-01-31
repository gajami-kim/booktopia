

document.addEventListener('click', (e) => {
    const target = e.target;

    if (target.classList.contains('admin-btn')) {
        // admin cate 선택 부분
        e.preventDefault();
        const btnId = target.id;
        handleButtonClick(btnId);
    }
    else if(e.target.classList.contains('adminapproval')){
        // 배송현황 > 결제승인 버튼을 클릭 했을 때...
        const tr = e.target.closest('tr');
        let deliUid = tr.querySelector('.deliUid').innerText;
        let deliStatus = tr.querySelector('.deliStatus').innerText;

        if(deliStatus === '배송준비중'){
            postStatus(deliUid).then(result =>{
                if (result == 1){
                    alert("배송처리 되었습니다.");
                }
            })
        } else {
            postSecondStatus(deliUid).then(result =>{
                if(result == 1){
                    alert("배송완료처리");
                }
            })
        }
    } else if (e.target.classList.contains('boardDel')){
        // 게시글 삭제 버튼을 눌렀을 때...
        const tr = e.target.closest('tr');
        let bno = tr.querySelector(".adbbno").innerText;
        boardDelToServer(bno).then(result =>{
            if(result === "1"){
                alert('게시글 삭제 완료되었습니다.');
                tr.parentNode.removeChild(tr);
            }
        })
    } else if(e.target.classList.contains('commentDel')) {
        // 댓글관리 페이지에서 삭제 버튼 눌렀을 때...
        const tr = e.target.closest('tr');
        let bno = tr.querySelector(".adcbno").innerText;
        CommentDelToServer(bno).then(result => {
            if (result === "1") {
                alert('댓글 삭제 되었습니다.');
                tr.parentNode.removeChild(tr);
            }
        })
    } else if(e.target.classList.contains('adminmoreBtn')){ // cate 페이지 별 더보기
        let page = parseInt(e.target.dataset.page);
        let cate = e.target.dataset.cate;
        spreadList(cate, page);
    } else if (e.target.id ==='postCoupon'){ // 쿠폰 등록 버튼을 눌렀을 때...
        const couName = document.getElementById('couName');
        const couPeriod = document.getElementById('couPeriod');
        const couSale = document.getElementById('couSale');
        const couInfo = document.getElementById('couInfo');

        let couponDate ={
            adCouName:couName.value,
            adCouPeriod:couPeriod.value,
            adCouSale:couSale.value,
            adCouInfo:couInfo.value
        };
        postCoupon(couponDate).then(result =>{
            if(result == 1){
                alert("쿠폰생성 완료");
                couName.value = '';
                couPeriod.value = '';
                couSale.value = '';
                couInfo.value = '';
                spreadList(cate, page);
            }
        })
    } else if (e.target.classList.contains('qnaStatus')){
        const num = e.target.dataset.num;
        qnaOneUserToServer(num).then(result =>{
            if(result[0].qnaStatus === '답변대기중'){
                document.getElementById('adminQnA').style.display = 'block';
                const div = document.getElementById('adminQnA');
                let p = `<p class="adminQnaTitle">1:1 문의글</p>`;
                p += `<p class="adminqnaNum" data-qna="${result[0].qnaNum}"></p>`;
                p += `<p class="bottom"><span style="margin-right: 78px" class="bold">id  </span><span>${result[0].id}</span></p>`
                p += `<p class="bottom"><span style="margin-right: 58px" class="bold">Title  </span><span>${result[0].qnaTitle}</span></p>`;
                p += `<p class="bottom bold">Content</p><p class="bottom">${result[0].qnaContent}</p>`;
                p += `<p class="bottom bold">Answer</p><p class="bottom"><textarea class="adminsAnswer"></textarea></p>`;
                p += `<button type="button" class="answerBtn">답변 회신</button>`;

                div.innerHTML += p;
            } else if(result[0].qnaStatus === '답변완료'){
                document.getElementById('adminQnA_answer').style.display = 'block';
                const div = document.getElementById('adminQnA_answer');
                let p = `<p class="adminQnaTitle">1:1 문의글 - 회신완료</p>`;
                p += `<p class="bottom"><span style="margin-right: 78px" class="bold">id  </span><span>${result[0].id}</span></p>`
                p += `<p class="bottom"><span style="margin-right: 58px" class="bold">Title  </span><span>${result[0].qnaTitle}</span></p>`;
                p += `<p class="bottom bold">Content </p><p class="bottom">${result[0].qnaContent}</p>`;
                p += `<p class="bottom bold">Answer </p><p class="bottom">${result[0].qnaAnswer}</p>`;
                p += `<button type="button" class="qnaClose">X</button>`
                div.innerHTML += p;
            }
        })
    } else if (e.target.classList.contains('answerBtn')){
        const qnaAnswer = document.querySelector('.adminsAnswer').value;
        const qnaNum = document.querySelector('.adminqnaNum').dataset.qna;

        qnaAnserToServer(qnaAnswer, qnaNum).then(result =>{
            alert("문의 답변 완료");

            updateQnaStatusToServer(qnaNum).then(result =>{
                document.getElementById('adminQnA').style.display = 'none';
                document.getElementById('adminQnA').innerHTML = '';
                document.querySelector('.qnaStatus').style.backgroundColor = "#005c87"
                document.querySelector('.qnaStatus').style.color = "white";
            })

        })
    } else if(e.target.classList.contains('qnaClose')){
        document.getElementById('adminQnA_answer').style.display = 'none';
        document.getElementById('adminQnA_answer').innerHTML = '';
    }
});



// 관리자 페이지 내용 변경 부분
function handleButtonClick(btnId) {
    const sections = ['.admin-UserList', '.bookTopia-user', '.subUser', '.delivery', '.commuBoard','.commuComment', '.adminCouponAdd','.adminqna'];
    let index = sections[btnId.slice(-1)-1];
    sections.forEach(section => {
        const displayStyle = section.includes(index) ? 'block' : 'none';
        document.querySelector(section).style.display = displayStyle;
    });

    if(index === '.admin-UserList'){ //회원리스트
        let cate = 'adUser';
        spreadList(cate);
    } else if(index === '.bookTopia-user'){ //취향검사 리스트
        let cate = 'adTest';
        spreadList(cate);
    } else if(index === '.subUser'){ // 구독자 리스트
        let cate = 'adsubUser'
        spreadList(cate);
    } else if(index === '.delivery'){ //배송 리스트
        let cate = 'addeli'
        spreadList(cate);
    } else if(index === '.commuBoard'){ // 게시글 리스트
        let cate = "addboard";
        spreadList(cate);
    }else if(index === '.commuComment') { // 댓글 리스트
        let cate = 'adCommen';
        spreadList(cate);
    } else if(index === '.adminCouponAdd'){
        let cate = 'adCoupon';
        spreadList(cate);
    } else if(index === '.adminqna'){
        let cate = 'adqna';
        spreadList(cate);
    }

    /* cate 버튼 옵션 변경 구문 */
    const buttons = document.querySelectorAll('.admin-btn');
    buttons.forEach(button => {
        button.style.backgroundColor = button.id === btnId ? '#005c87' : '';
        button.style.color = button.id === btnId ? '#ffffff' : '';
        button.style.fontWeight = button.id === btnId ? '700' : '';
    });
}

// 카테고리 별로 뿌리는...
function spreadList(cate, page=1){
    switch (cate){
        case "adUser" : // 회원 리스트 뿌리기
            userListToServer(page).then(result=>{
                const tbody = document.getElementById('adminUserList');
                if(result.userList.length > 0){
                    if(page === 1){
                        tbody.innerHTML = '';
                    }
                    for(let uvo of result.userList){
                        let td = `<td style="text-align: center">${uvo.id}</td>`;
                        td += `<td style="text-align: center">${uvo.name}</td>`;
                        td += `<td style="text-align: center">${uvo.email}</td>`;
                        td += `<td style="text-align: center">${uvo.phone}</td>`;
                        td += `<td style="text-align: center">${uvo.userType}</td>`;
                        td += `<td style="text-align: center">${uvo.userReg}</td></tr>`;

                        tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminUserBtn');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
        case "adTest" : // 취향검사 리스트 뿌리기
            bookTopiaToServer(page).then(result =>{
                const tbody = document.getElementById('adminTestList');
                if(result.booktopia.length > 0) {
                    if (page === 1) {
                        tbody.innerHTML = '';
                    }
                    for (let test of result.booktopia) {
                        let td = `<td>${test.id}</td>`;
                        td += `<td>${test.birth}</td>`;
                        td += `<td>${test.gender}</td>`;
                        td += `<td>${test.testAt}</td>`;
                        td += `<td>${test.btnResult}</td>`;

                        tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminTestBtn');
                    if (result.pgvo.pageNo < result.realEndPage) {
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page + 1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
        case "adsubUser" : // 구독리스트 뿌리기
            subUserToServer(page).then(result => {
                const tbody = document.getElementById('adminSubList');
                if(result.orderInfoDTOList.length > 0){
                    if(page === 1){
                        tbody.innerHTML = '';
                    }
                    for(let sub of result.orderInfoDTOList){
                        let td = `<td style="text-align: center">${sub.merchantUid}</td>`
                        td += `<td style="text-align: center">${sub.itemName}</td>`;
                        td += `<td style="text-align: center">${sub.ordName}</td>`;
                        td += `<td style="text-align: center">${sub.ordPhone}</td>`;
                        td += `<td style="text-align: center">${sub.ordAddr}</td>`;
                        td += `<td style="text-align: center">${sub.totalAmount}</td>`;

                        tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminSubBtn');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
        case "addeli" : // 배송현황 리스트 뿌리기
            deliToServer(page).then(result => { //배송 리스트
                const tbody = document.getElementById('adminDeliList');
                if(result.deliveries.length > 0){
                    if(page === 1){
                        tbody.innerHTML = '';
                    }
                    for (let deli of result.deliveries){
                        let td = `<td class="deliUid">${deli.merchantUid}</td>`;
                        td += `<td>${deli.itemName}</td>`;
                        td += `<td>${deli.deliDate}</td>`;
                        td += `<td class="deliStatus" style="width: 110px;">${deli.deliStatus}</td>`;
                        td += `<td><button type="button" class="adminapproval">${deli.deliStatus}</button></td>`;

                        tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminDeliBtn');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                } else {
                    tbody.innerHTML = `<div> List Empty </div>`;
                }
            })
            break;
        case "addboard" : // 게시글 리스트 뿌리기
            communBoardToServer(page).then(result =>{
                const tbody = document.getElementById('adminBoardList');
                if(result.boardlist.length > 0){
                    if(page === 1){
                        tbody.innerHTML = '';
                    }
                    for (let board of result.boardlist){
                        let td =  `<td style="text-align: center" class="adbbno">${board.bno}</td>`;
                        td += `<td>${board.bcate}</td>`;
                        td += `<td>${board.btitle}</td>`;
                        if(board.bmainImg!== ''){
                            td += `<td><img src="/upload/${board.bmainImg}" style="height: 200px">`
                            // td += `<div class="adcontent">${board.bcontent}</div></td>`;
                        } else {
                            td += `<td class="contentWrap"><div class="adcontent">${board.bcontent}</div></td>`;
                        }
                        td += `<td>${board.bwriter}</td>`;
                        td += `<td>${board.bregDate}</td>`;
                        td += `<td><button type="button" class="boardDel">삭제</button></td>`;

                        tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminBoardBtn');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
        case "adCommen" : // 댓글 리스트 뿌리기
            commentBoardToServer(page).then(result=>{
                const tbody = document.getElementById('adminComment');
                if(result.cmtList.length > 0){
                    if (page === 1){
                        tbody.innerHTML = '';
                    }
                    for (let adcomment of result.cmtList){
                        let td = `<td class="adcbno" style="text-align: center">${adcomment.bno}</td>`;
                        td += `<td style="text-align: center">${adcomment.cwriter}</td>`;
                        td += `<td style="padding-left: 20px">${adcomment.ccontent}</td>`;
                        td += `<td style="text-align: center">${adcomment.cregDate}</td>`;
                        td += `<td><button type="button" class="commentDel">삭제</button></td>`;

                        tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminCommBtn');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
        case "adCoupon" : // 쿠폰 리스트 뿌리기
            couponToServer(page).then(result =>{
                const tbody = document.getElementById('addCoupon');
                if(result.adCouponList.length>0){
                    if(page === 1){
                        tbody.innerHTML = '';
                    }
                    for (let coupon of result.adCouponList){
                        let td = `<td>${coupon.couNo}</td>`;
                            td += `<td>${coupon.adCouName}</td>`;
                            td += `<td>${coupon.adCouPeriod}</td>`;
                            td += `<td>${coupon.adCouSale}</td>`;
                            td += `<td>${coupon.adCouDate}</td>`;
                            td += `<td>${coupon.adCouInfo}</td>`;

                            tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminCoupon');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
        case "adqna" : // 문의글 뿌리기
            getQnaList(page).then(result =>{
                const tbody= document.getElementById('adminqnaList');
                if(result.qnaList.length > 0){
                    if (page === 1){
                        tbody.innerHTML = '';
                    }
                    for (let qna of result.qnaList){
                        let td = `<td style="text-align: center">${qna.qnaNum}</td>`;
                            td += `<td class="qnaId" style="text-align: center">${qna.id}</td>`;
                            td += `<td style="text-wrap: wrap; line-height: 20px">${qna.qnaTitle}</td>`;
                            td += `<td style="text-wrap: wrap; line-height: 20px">${qna.qnaContent}</td>`;
                            td += `<td>${qna.qnaRegAt}</td>`;
                            td += `<td><button type="button" class="qnaStatus" data-num="${qna.qnaNum}" 
                                    data-id="${qna.id}" data-title="${qna.qnaTitle}" data-content="${qna.qnaContent}"
                                    data-status="${qna.qnaStatus}">${qna.qnaStatus}</button></td>`;
                            tbody.innerHTML += td;
                    }
                    let moreBtn = document.getElementById('adminQnABtn');
                    if(result.pgvo.pageNo < result.realEndPage){
                        moreBtn.style.visibility = 'visible';
                        moreBtn.dataset.page = page+1;
                    } else {
                        moreBtn.style.visibility = 'hidden';
                    }
                }
            })
            break;
    }
}

/*
   프론트 : N페이지 회원 리스트를 "/admin/getUserList/"+pageNo 주소로 서버한테 요청
   서버 : GET ("/getUserList/{pageNo}")
         PagingVO > N페이지 보여질 갯수는 10개(qty)로 정하고
         리스트에 전체 갯수를 totalCount로 받아오고
         PagingHandler ph > 이전에 만든 PagingVO와 totalCount를 넣어줌
         내가 가지게 되는 정보 값 > startPage / endPage / prev, next / totalCount / pgvo
         Mapper에서 list 가지고 올 때 limit (pageStart, qty) 조건으로 넣으면
         내가 요청한 페이지에 대한 목록을 받음 (ResponseBody) > json
   프론트 : 리스트를 html에 뿌려
*/

// 회원리스트 요청
async function userListToServer(pageNo){
    const resp = await fetch("/admin/getUserList/"+pageNo);
    const result = await resp.json();
    return result;
}

//취향검사 리스트 요청
async function bookTopiaToServer(pageNo){
    const resp = await fetch("/admin/testList/"+pageNo);
    const result = await resp.json();
    return result;
}

// 구독자리스트(결제)
async function subUserToServer(pageNo) {
    const resp = await fetch("/admin/subUser/"+pageNo);
    const result = await resp.json();
    return result;
}

// 배송 리스트
async function deliToServer(pageNo){
    const resp = await  fetch("/admin/delivery/"+pageNo);
    const result = await resp.json();
    return result;
}

// 게시글관리
async function communBoardToServer(pageNo){
    const resp = await  fetch("/admin/boardList/"+pageNo);
    const result = await resp.json();
    return result;
}

// 댓글 관리
async function commentBoardToServer(pageNo){
    const resp = await fetch("/admin/admincommentList/"+pageNo);
    const result = await resp.json();
    return result;
}

// 쿠폰관리 리스트 뿌리기
async function couponToServer(pageNo){
    const resp = await fetch("/admin/adminCoupon/"+pageNo);
    const result = await resp.json();
    return result;
}

// 쿠폰생성
async function postCoupon(couponDate){
    try {
        const url = "/admin/addCoupon";
        const config = {
            method : "POST",
            headers : {
                "Content-type":"application/json; charset=UTF-8"
            },
            body : JSON.stringify(couponDate)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch (error){
        console.log(error);
    }
}

// 문의글 리스트 가져오기
async function getQnaList(pageNo){
    const resp = await fetch("/admin/adminQnaList/"+pageNo);
    const result = await resp.json();
    return result;
}

// 하나의 데이터만 불러오기
async function qnaOneUserToServer(num){
    try{
        const url = "/admin/adminOneUser/"+num;
        const config = {
            method : "POST",
            headers : {
                "Content-type":"application/json; charset=UTF-8"
            },
            body : JSON.stringify(num)
        };

        const resp = await fetch(url, config);
        const result = await resp.json();
        return result;

    }catch (erorr){
        console.log(error);
    }
}

// 문의글 답변 전달하기
async function qnaAnserToServer(qnaAnswer, qnaNum){
    try{
        const url = "/admin/qnaAnswer/"+qnaAnswer+"/"+qnaNum;
        const config = {
            method : "POST",
            headers : {
                "Content-type": "application/json; charset=UTF-8"
            },
            body : JSON.stringify(qnaAnswer)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch (error){
        console.log(error);
    }
}

async function updateQnaStatusToServer(qnaNum){
    try {
        const url = "/admin/qnaStatus/"+qnaNum;
        const config = {
            method : "POST",
            headers : {
                "Content-type":"application/json; charset=UTF-8"
            },
            body : JSON.stringify(qnaNum)
            };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
        }catch (error){
            console.log(error);
        }
}

async function getQnaStatusFromServer(qnaNum){
    try {
        const url = "/admin/qnaStatus/"+qnaNum;
        const config = {
            method : "GET",
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch (error){
        console.log(error);
    }

}

// 게시글 관리 > 삭제
async function boardDelToServer(bno){
    try {
        const url = "/admin/boardDel/"+bno;
        const config = {
            method : 'delete',
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error){
        console.log(error);
    }
}

// 댓글 관리 > 삭제
async function CommentDelToServer(bno){
    try {
        const url = "/admin/commentDel/"+bno;
        const config = {
            method : 'delete',
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error){
        console.log(error);
    }
}

// 배송현황 버튼 처리(처음클릭)
async function postStatus (deliUid){
    try {
        const url = "/admin/deliUid";
        const config = {
            method : "POST",
            headers : {
                "Content-type":"application/json; charset=UTF-8"
            },
            body : JSON.stringify(deliUid)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;

    }catch (error){
        console.log(error);
    }
}

// 배송현황 버튼 처리(두번째 클릭)
async function postSecondStatus (deliUid){
    try {
        const url = "/admin/secondStatus";
        const config = {
            method : "POST",
            headers : {
                "Content-type":"application/json; charset=UTF-8"
            },
            body : JSON.stringify(deliUid)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;

    }catch (error){
        console.log(error);
    }
}

