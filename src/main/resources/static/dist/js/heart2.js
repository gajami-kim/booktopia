const heartBtn = document.querySelector('.hrtBtn');
const userId = document.querySelector('.commDeUserId').value;

document.querySelector('.hrtBtn').addEventListener('click',()=>{
    getUserBno(bnoVal,userId).then(result=>{
        if(result==='1'){
            deleteHeart(bnoVal, userId).then(result=>{
                heartBtn.innerHTML='';
                heartBtn.innerHTML=`<img src="/image/heart_white.png" class="whiteHeart">`;
            })
        } else {
            let data={
                id:userId,
                bno:bnoVal,
                heartYn:1
            };

            clickHeart(data).then(result=>{
                heartBtn.innerHTML='';
                heartBtn.innerHTML=`<img src="/image/heart.png" class="redHeart">`
            })
        }
    })
})

async function clickHeart(data){
    try {
        const url = "/board/heart/" + bnoVal;
        const config = {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(data)
        };

        const resp = await fetch(url, config)
        const result = await resp.text();
        return result;
    } catch(error) {
        console.log(error)
    }
}

async function deleteHeart(bno,id){
    try{
        const url="/board/heart/delete/"+bno+"/"+id;
        const config = {
            method:"delete"
        }

        const resp = await fetch(url, config)
        const result = await resp.text();
        return result;
    }catch(error){
        console.log("delete error "+error);
    }
}

//bno 떼오는 함수
async function getUserBno(bno,id){
    try{
        const url = "/board/heart/"+bno+"/"+id;
        const config ={
            method:"GET",
            headers:{
                'content-type':'application/json; charset=UTF-8'
            }
        };

        const resp = await fetch(url,config);
        const result = await resp.text();
        return result;
    }catch(error) {
        console.log(error);
    }
}
