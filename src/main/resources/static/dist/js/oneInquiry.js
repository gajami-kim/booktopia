var inquiryId= document.getElementById("realId");

var inquiryIdVal = inquiryId.innerText;

getAnsList(inquiryIdVal).then(result=>{

    const ul = document.getElementById("QnaResult");
    if(result.length == 0){
        ul.innerHTML = `<div class="QnaResultListDiv">현재 문의하신 내역이 없습니다.</div>`;
    }else{
        result.forEach(item => {
            const li = document.createElement("li");
            li.innerHTML =
            `<div class="qnaWrap">
                 <div class="qnaregatdiv">
                    <p>${item.qnaRegAt.substring(0,10)}</p>
                 </div>
                <div class="qnatitlediv">
                    <p>${item.qnaTitle}</p>
                </div>
                <div class="qnacontentdiv">
                    <p>${item.qnaContent}</p>
                </div>
                ${item.qnaAnswer == null ?
                   '<div class="answernoIcon"><img src="/image/qnaanswerenter.png" alt="Answer Icon"></div><div class="qnanoanswerdiv"><p>아직 답변을 받지 못했습니다.</p></div>'
                   : `<div class="answerIcon"><img src="/image/qnaanswerenter.png" alt="Answer Icon"></div>
                   <div class="qnaanswerdiv"><p>${item.qnaAnswer}</p></div>`
                   }
            </div>`;
        ul.appendChild(li);
        });
    }
});

async function getAnsList(id){
    const resp = await fetch("/qna/oneInquirylist/"+id);
    const result = resp.json();
    return result;
}