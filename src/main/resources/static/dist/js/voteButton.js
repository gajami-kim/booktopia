let score = parseInt(document.querySelector('.socre').value);

let downPer = 0;
const upBtn = document.querySelector('.upBtn').value;
const downBtn = document.querySelector('.downBtn').value;

document.querySelector('.persent').style.width = `${score}%`;

document.addEventListener('click',(e)=>{
    if(e.target.tagName === "IMG"){
        if (e.target.id === 'upImg'){
            getIdToServer(user).then(result=>{
                if(result === '1'){
                    alert('이미 참여한 이벤트입니다.')
                } else if(user === 'anonymousUser'){
                    alert('로그인 후 참여가능합니다.');
                    // e.target.disabled = true;
                } else {
                    score +=3;
                    downPer = 100-score;
                    document.querySelector('.persent').style.width = `${score}%`;

                    let data ={
                        id:user,
                        voteResult:'찬성'
                    };

                    push(data).then(result=>{
                        console.log(result);
                    });

                    console.log("score >> "+score)
                    console.log("downper >> "+downPer);
                }
            })
        } else if(e.target.id === 'downImg'){
            getIdToServer(user).then(result=>{
                console.log("결과값 >>> "+result);
                if(result === '1'){
                    console.log("123456");
                    alert('이미 참여한 이벤트입니다.')
                } else if(user === 'anonymousUser'){
                    alert('로그인 후 참여가능합니다.');
                    // e.target.disabled = true;
                } else {
                    console.log(downBtn);
                    score -=3;
                    // downPer = 100-score;
                    document.querySelector('.persent').style.width = `${score}%`;
                    let data ={
                        id:user,
                        voteResult:'반대'
                    };

                    push(data).then(result=>{
                        console.log(result);
                    });
                }

            })
        }
    }
})
console.log("user >> " +user);


async function push(data){
    const url ="/community/push";
    const config = {
        method : "POST",
        headers : {
            "Content-type":"application/json; charset=UTF-8"
        },
        body:JSON.stringify(data)
    };

    const resp = await fetch(url, config);
    const result = await resp.text();
    return result;
}

async function getIdToServer(user) {

    try {
        const url = "/community/"+user;
        const config = {
            method: "GET",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        };

        const resp = await fetch(url, config)
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// 누적 점수를 가져오는 비동기
async function getScoreToServer(){
    try{
        const url ="/system/getScore";
        const config = {
            method : "GET",
            headers:{
                "Content-type": "application/json; charset=UTF-8"
            }
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch (error){
        console.log(error);
    }
}