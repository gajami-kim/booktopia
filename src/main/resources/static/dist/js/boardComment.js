const commDeUserId = document.querySelector('.commDeUserId').value;
const commDeUserEmail = document.querySelector('.commDeUserEmail').value;
const socialId = document.querySelector('.socialID').value;

document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cContent  = document.getElementById('cmtContent').value;

    if(cContent==null||cContent=='') {
        alert("댓글을 입력해주세요.");
        document.getElementById('cmtContent').focus();
        return false;
    } else {
        if(socialId!='일반'){
            let cmtData = {
                bno:bnoVal,
                cWriter:commDeUserEmail,
                cContent:cContent
            }
            postComment(cmtData).then(result=>{
                if(result=="1") {
                    document.getElementById('cmtContent').value='';
                    spreadCommentList(bnoVal);
                }
            })
        } else {
            let cmtData = {
                bno:bnoVal,
                cWriter: commDeUserId,
                cContent:cContent
            }
            postComment(cmtData).then(result=>{
                if(result=="1") {
                    document.getElementById('cmtContent').value='';
                    spreadCommentList(bnoVal);
                }
            })
        }

    }
})

async function postComment(cmtData) {
    try {
        const url = "/comment/post";
        const config={
            method:"POST",
            headers:{
                'Content-Type': 'application/json; charset=utf8'
            },
            body:JSON.stringify(cmtData)
        };
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log("postComment error: "+error);
    }
}

async function getCommentList(bno,page){
    try{
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log("getCommentList error : "+error);
    }
}

function spreadCommentList(bno,page=1) {
    getCommentList(bno,page).then(result=>{
        const ul=document.querySelector(".commDeOneComment");
        const rc = document.querySelector('.commDe-ReComment');
        if(result.cmtList.length>0) {
            if(page==1) {
                ul.innerHTML='';
            }
            for(let obj of result.cmtList){

                let li = `<div class="commDeCommentArea" data-cno="${obj.cvo.cno}" value="${obj.cvo.cno}" id="commDeCommentAreaId">`;
                li+=`<div class="commImg"><img src="/image/profile2.png"></div>`;
                li+=`<div class="commComInfo"><span class="commDe-Writer">${obj.cvo.cwriter}&nbsp</span><span>${obj.cvo.cregDate.substring(0,16)}</span>`;
                li+=`<div class="commDe-ReComment"><button type="button" id="recommendBtn">대댓글 달기</button></div>`;
                li+=`<div class="commDe-Content" id="commentContent">${obj.cvo.ccontent}</div></div>`
                li+=`<div class="recomm" data-cno="${obj.cvo.cno}"></div>`
                //수정, 삭제 버튼
                if(commDeUserId===obj.cvo.cwriter || commDeUserEmail==obj.cvo.cwriter){
                    li+=`<div class="cmtBtn">`
                    li+=`&nbsp;<button type="button" id="cmtModBtn" class="commComModBtn">수정</button>`;
                    li+=`&nbsp;<button type="button" id="comDelBtn" data-cno="${obj.cvo.cno}" class="commComDelBtn">삭제</button>`;
                    li+=`</div>`
                }
                for(let rcvo of obj.rclist){
                    li+=`<div class="commDeReCommentArea" data-cno="${rcvo.cno}">`;
                    li+=`<div class="commImg2"><img src="/image/profile2.png"></div>`;
                    li+=`<div class="commDeReInfo"><span class="commDe-Writer">${rcvo.rcWriter}&nbsp</span><span>${rcvo.rcRegDate.substring(0,16)}</span>`;
                    li+=`<div class="commDe-Content" id="commentContent">${rcvo.rcContent}</div></div>`
                }
                li+=`</div>`;
                ul.innerHTML+=li;
            }
        } else {
            ul.innerHTML=`<div class="commDeNon"></div>`;
        }
    }).catch(error => {
        console.error("Error fetching comments:", error);
    });
}

document.addEventListener('click',(e)=>{
    if(e.target.id==='recommendBtn'){
        const cContent = e.target.closest('.commDeCommentArea');
        const cno = cContent.getAttribute('value');
        let plusData = `<div class="reCommentArea" id="recomment">`
        plusData+=`<textarea type=text placeholder="댓글을 입력해주세요" id="recommContent"></textarea>`;
        plusData+=`<button type="button" id="recommBtn">등록</button>`;
        plusData+=`</div>`
        cContent.innerHTML+=plusData;
        document.getElementById('recommBtn').addEventListener('click',()=>{
            if(document.getElementById('recommContent').value==null || document.getElementById('recommContent').value==''){
                alert("댓글을 입력해주세요.")
                document.getElementById('recommContent').focus();
            } else {
                if(commDeUserEmail==null||commDeUserEmail==''){
                    let reCmtData = {
                        cno:cno,
                        bno:bnoVal,
                        rcWriter:commDeUserId,
                        rcContent:document.getElementById('recommContent').value
                    }
                    postReCommentToServer(reCmtData).then(result=>{
                        if(result==='1'){
                            spreadCommentList(bnoVal)
                        }
                    })
                } else {
                    let reCmtData = {
                        cno:cno,
                        bno:bnoVal,
                        rcWriter:commDeUserEmail,
                        rcContent:document.getElementById('recommContent').value
                    }
                    postReCommentToServer(reCmtData).then(result=>{
                        if(result==='1'){
                            spreadCommentList(bnoVal)
                        }
                    })
                }
            }
        })
    }
})

async function postReCommentToServer(reCmtData) {
    try {
        const url = "/comment/post/comment";
        const config ={
            method:"post",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body:JSON.stringify(reCmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text(); //return=>isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}

//수정
async function getModComment(cmtModData) {
    try{
        const url="/comment/modify";
        const config = {
            method:"PUT",
            headers:{
                'Content-Type': 'application/json; charset=utf8'
            },
            body:JSON.stringify(cmtModData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log("mod error : "+error);
    }
}

//삭제
async function removeComment(cnoVal){
    try{
        const url = "/comment/"+cnoVal;
        const config ={
            method:"delete"
        }
        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log("remove error : "+error);
    }
}

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('commComModBtn')){

        const cContent = e.target.closest('.commDeCommentArea');
        const cvArea = cContent.querySelector('.commDe-Content');
        let cv = cContent.querySelector('.commDe-Content').innerHTML;
        cvArea.innerHTML='';

        let cno = cContent.getAttribute('value');

        let modComment = `<textarea type="text" id="cmtModContent" data-cno="${cno}">${cv}</textarea>`;

        cContent.querySelector('.cmtBtn').innerHTML='';
        document.querySelector('.cmtBtn').innerHTML+=modComment;
        document.querySelector('.cmtBtn').innerHTML+=`<button type="button" id="commModBtn">수정하기</button>`;

    } else if(e.target.id==='commModBtn'){
        const cContent = e.target.closest('.commDeCommentArea');
        let cmtModData ={
            cno:cContent.getAttribute('value'),
            cContent: document.getElementById('cmtModContent').value
        };

        getModComment(cmtModData).then(result=>{
            if(result==='1'){
                console.log("댓글수정완료");
            }else {
                console.log("댓글수정실패");
            }
            spreadCommentList(bnoVal);
        })
    }
    //삭제
    else if(e.target.classList.contains('commComDelBtn')){
        const delCom = confirm('댓글을 삭제하시겠습니까? \n댓글 삭제 시 대댓글도 함께 삭제됩니다.');
        let cnoVal = e.target.dataset.cno;
        if(delCom===true) {
            removeComment(cnoVal).then(result => {
                if (result === '1') {
                    alert('댓글을 삭제하였습니다.')
                    spreadCommentList(bnoVal);
                } else {
                    console.log("댓글삭제실패")
                }
            })
        }
    }
})