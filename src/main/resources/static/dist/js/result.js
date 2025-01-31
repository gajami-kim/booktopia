document.addEventListener('DOMContentLoaded', () => {
    let user = document.getElementById('commuUser');


    getIdToServer(user.value).then(result => {
        const main = document.getElementById("testResult");
        main.innerHTML = ""; // ul 내용 초기화

        if (result == null) {
            main.innerHTML = `     
                    <div class="msg">
                    <div class="noResInfo">검사를 먼저 시도해주세요. </div>
                    <a href="/booktopiaTest/test">검사하러가기</a>
                                    </div>`;
        } else {
            // 결과가 있는 경우 각 항목을 ul에 추가
            const div = document.createElement("div");
            div.innerHTML = `
                <h2 class="titleMsg">회원님과 딱 맞는 도서</h2>`;
            result.forEach(list => {
                // 새로운 리스트 아이템 생성
                // 각 필드 값을 리스트 아이템에 추가
                div.innerHTML += `
                <a href="/booktopiaTest/detail?book=${list.bookCode}">
                <div class="bookWrap">     
                <img class="bookImg" src="/image/book2/book${list.bookCode}.jpg">
                </div>
                </a>`;
                // ul에 리스트 아이템 추가
                main.appendChild(div);
            });
            const div2 = document.getElementById("bestBookList");
            div2.innerHTML = `
             <h2 class="titleMsg2">인기 도서</h2>
             <div class="mybestbook">
            <div class="bblist original">
             <a href="/booktopiaTest/detail?book=2">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book2.jpg">
             <div class="bookinfo">
             <em class="ranking">1</em>
             <div class="bestbooktitle">일차원이 되고 싶어</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=7">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book7.jpg">
                 <div class="bookinfo">
             <em class="ranking">2</em>
             <div class="bestbooktitle">일편단시</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=4">
             <div class="bestBook =">
             <img class="bestbookimg" src="/image/book2/book4.jpg">
             <div class="bookinfo">
             <em class="ranking">3</em>
             <div class="bestbooktitle">우리가 빛의 속도로 갈 수 없다면</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=36">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book36.jpg">
             <div class="bookinfo">
             <em class="ranking">4</em>
             <div class="bestbooktitle">역행자</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=11">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book11.jpg">
             <div class="bookinfo">
             <em class="ranking">5</em>
             <div class="bestbooktitle">내 삶의 이야기를 쓰는 법</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=33">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book33.jpg">
             <div class="bookinfo">
             <em class="ranking">6</em>
             <div class="bestbooktitle">거인의 노트</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=8">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book8.jpg">
             <div class="bookinfo">
             <em class="ranking">7</em>
             <div class="bestbooktitle">하늘과 바람과 별과 시</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=30">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book30.jpg">
             <div class="bookinfo">
             <em class="ranking">8</em>
             <div class="bestbooktitle">예루살렘의 역사</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=22">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book22.jpg">
             <div class="bookinfo">
             <em class="ranking">9</em>
             <div class="bestbooktitle">도둑맞은 집중력</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=19">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book19.jpg">
             <div class="bookinfo">
             <em class="ranking">10</em>
             <div class="bestbooktitle">생각이 많은 어른들을 위한 심리학</div>
             </div>
             </div>
             </a>
            </div>
             </div>
            `;

            div2.innerHTML += `
             <h2 class="titleMsg2">추천 도서</h2>
             <div class="mybestbook2">
            <div class="bblist original">
             <a href="/booktopiaTest/detail?book=3">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book3.jpg">
             <div class="bookinfo">
             <em class="ranking">1</em>
             <div class="bestbooktitle">러브 레플리카</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=6">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book6.jpg">
                 <div class="bookinfo">
             <em class="ranking">2</em>
             <div class="bestbooktitle">비가 오면 열리는 상점</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=10">
             <div class="bestBook =">
             <img class="bestbookimg" src="/image/book2/book10.jpg">
             <div class="bookinfo">
             <em class="ranking">3</em>
             <div class="bestbooktitle">아이 러브 퍼퓸</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=12">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book12.jpg">
             <div class="bookinfo">
             <em class="ranking">4</em>
             <div class="bestbooktitle">베르베르씨, 오늘은 뭘 쓰세요?</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=15">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book15.jpg">
             <div class="bookinfo">
             <em class="ranking">5</em>
             <div class="bestbooktitle">부의 추월차선</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=16">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book16.jpg">
             <div class="bookinfo">
             <em class="ranking">6</em>
             <div class="bestbooktitle">부자의 언어</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=28">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book28.jpg">
             <div class="bookinfo">
             <em class="ranking">7</em>
             <div class="bestbooktitle">상하이의 유대인 제국</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=31">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book31.jpg">
             <div class="bookinfo">
             <em class="ranking">8</em>
             <div class="bestbooktitle">더 마인드</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=29">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book29.jpg">
             <div class="bookinfo">
             <em class="ranking">9</em>
             <div class="bestbooktitle">요즘 어른들을 위한 최소한의 세계사</div>
             </div>
             </div>
             </a>
             <a href="/booktopiaTest/detail?book=20">
             <div class="bestBook">
             <img class="bestbookimg" src="/image/book2/book20.jpg">
             <div class="bookinfo">
             <em class="ranking">10</em>
             <div class="bestbooktitle">쇼펜하우어 아포리즘</div>
             </div>
             </div>
             </a>
            </div>
             </div>
            `;

        }
    });

});

async function getIdToServer(user) {
    try {
        const resp = await fetch("/booktopiaTest/resultTest/"+user);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}
