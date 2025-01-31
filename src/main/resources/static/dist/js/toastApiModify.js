const commDeUserId = document.querySelector('.commDeUserId').value;
const commDeUserEmail = document.querySelector('.commDeUserEmail').value;

let bMainImg='';

const editor = new toastui.Editor({
    el: document.querySelector('#editor2'),
    height: '500px',
    previewStyle: 'vertical',
    initialEditType:'markdown',
    toolbarItems:[
        ['heading','bold','italic','strike'],
        ['hr','quote'],
        ['ul', 'ol'],
        ['image']
    ],
    hooks:{
        addImageBlobHook:async (blob,callback)=> {
            try{
                const formData = new FormData();
                formData.append("image",blob);

                const response = await fetch("/file/file-upload",{
                    method:"POST",
                    body:formData
                });
                const fileName = await response.text();
                if(bMainImg==='') {
                    bMainImg=fileName;
                }

                const imageUrl = `/file/filePrint?fileName=${fileName}`;
                callback(imageUrl,"image print success");
            } catch (error){
                console.log("이미지 업로드 실패");
            }
        }
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const ul = document.querySelector('.commul');
    ul.addEventListener("click", (e)=> {
        if (e.target.tagName === "LI") {}
    });


    const cateBtn = document.querySelector('.cateBtn');
    const commul = document.getElementById('commSelID2');
    let selectedText = document.getElementById('selectedText2');

    cateBtn.addEventListener('click', function () {
        commul.classList.toggle('show');
    });

    const commliItems = document.querySelectorAll('.commli');
    commliItems.forEach(li=>{
        li.addEventListener('click',function (event){
            const bCate = event.target.textContent;
            selectedText.textContent = bCate;
            commul.classList.remove('show');
        });
    })

});



async function handleEditor(event) {

    try {
        const selectedText = document.getElementById('selectedText2');
        let bCate = selectedText.innerText;
        if(bCate==="소설"){
            bCate ='novel';
        } else if(bCate==="시/에세이"){
            bCate="poem"
        } else if(bCate==="경제/경영") {
            bCate="finance"
        } else if(bCate==="인문"){
            bCate="human"
        } else if(bCate==="역사/문화"){
            bCate="history"
        } else if(bCate==="자기개발"){
            bCate="development"
        }

        const bno = document.querySelector('.commModBno').value;
        let postData = {};

        function updatePostData() {
            const bContent = editor.getMarkdown();
            const bTitle = document.getElementById('commTitleID2').value

            if(commDeUserEmail==null || commDeUserEmail=='') {
                postData = {
                    bno:bno,
                    id: commDeUserId,
                    bTitle: bTitle,
                    bWriter: commDeUserId,
                    bContent: bContent,
                    bCate: bCate,
                    bMainImg: bMainImg
                };
            } else if(commDeUserEmail!==null || commDeUserEmail!==''){
                postData={
                    bno:bno,
                    id: commDeUserId,
                    bTitle: bTitle,
                    bWriter: commDeUserEmail,
                    bContent: bContent,
                    bCate: bCate,
                    bMainImg: bMainImg
                }
            }

        }

        let isValid = true;

        if(bCate =='게시판 선택') {
            alert("게시판을 선택해주세요.");
            isValid = false;
        } else if (document.getElementById('commTitleID2').value == '' || document.getElementById('commTitleID2').value == null) {
            alert("제목을 입력해주세요.");
            document.getElementById('commTitleID2').focus();
            isValid = false;
        } else if (editor.getMarkdown() < 1) {
            alert("내용을 입력해주세요.");
            isValid = false;
        }

        if(isValid) {
            updatePostData();
            try{
                await submitPostData(postData);
                alert("게시물이 수정되었습니다.");
                window.location.href = "/board/detail?bno="+bno+"&id="+commDeUserId;
            } catch (error) {
                console.log("isValid error : ", error);
            }
        }
    } catch (error) {
        console.error("handleEditor error : ", error);
    }
}

    async function submitPostData(postData) {
        try {
            const url = "/board/modify";
            const config = {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf8'
                },
                body: JSON.stringify(postData)
            };

            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        } catch (error) {
            console.log(error)
        }
    }



