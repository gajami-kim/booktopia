window.addEventListener('load', function() {
    document.getElementById('findbtn').addEventListener('click', () => {
        let name = document.getElementById('fi_name').value;
        let resArea = document.getElementById('resArea');

        resArea.style.display = '';
        resArea.innerText = '';

        if (!name) {
            resArea.innerText = "이름을 입력해주세요.";
            return;
        }

        findUser(name).then(result => {
            if (!result) {
                resArea.innerText = "가입된 유저가 아니거나 아이디 정보가 없습니다.";
            } else {
                resArea.innerText = "아이디는 < " + result + " > 입니다.";
                resArea.innerHTML += `<br>`;
                resArea.innerHTML += `<br>`;
                resArea.innerHTML += `<a href="/user/login" class="fi_gotologin">로그인하러 가기</a>`;
            }
        });
    });

    // 이름으로 유저 찾기
    async function findUser(userName) {
        try {
            const url = '/user/findId/' + userName;
            const config = {
                method: 'GET'
            };

            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;

        } catch (error) {
            console.log(error);
        }
    }
});
