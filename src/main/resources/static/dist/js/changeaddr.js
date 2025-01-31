console.log("arr in js");

        var changeAddrid = document.getElementById("realId");
        var phone = document.getElementById('changePhone');
        var phoneRegex = /^010[0-9]{8}$/; // 휴대폰 유효성 검사 정규식
        var modid = document.getElementById("modaddrname");
        var addrone = document.getElementById("changeAddr");
        var addrtwo = document.getElementById("addrDetailInput");
        var submitAddrButton = document.getElementById("submitAddrButton");

        callinfo(changeAddrid.innerText).then(result => {
            phone.value = result.phone;
            addrone.value = result.address.substring(0, result.address.indexOf("/"));
            addrtwo.value = result.address.substring(result.address.indexOf("/") + 1);
        });

        phone.addEventListener("keyup", () => {
            submitAddrButton.disabled = !strongphone(phone.value);

        });

        document.getElementById('payGetAddrBtn').addEventListener('click', () => {
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('changeAddr').value = data.address;
                    document.getElementById('addrDetailInput').value = "";
                    document.getElementById('addrDetailInput').focus();
                }
            }).open();
        });

        document.getElementById("submitAddrButton").addEventListener("click", () => {
            let modidVal = modid.value;
            let addr = document.getElementById('changeAddr').value;
            let addrdetail = document.getElementById("addrDetailInput").value;
            let addall = addr + "/" + addrdetail;
            let modphone = document.getElementById("changePhone").value;

            const moddata = {
                id: modidVal,
                address: addall,
                phone: modphone
            };

            modifyaddr(moddata).then(result => {
                if (result == 1) {
                    alert("수정이 완료되었습니다.");
                    logout();
                } else {
                    alert("수정을 하지 못했습니다.");
                }
            });
        });

        function strongphone(str) {
            return phoneRegex.test(str) || str.length == 0;
        }

        async function logout() {
            try {
                const response = await fetch('/user/logout');
                if (response.ok) {
                    window.location.href = '/login'; // 로그아웃 후 로그인 페이지로 리디렉션
                } else {
                    alert('로그아웃에 실패했습니다.');
                }
            } catch (error) {
                console.error('Logout error:', error);
                alert('로그아웃 중 오류가 발생했습니다.');
            }
        }

        async function modifyaddr(moddata) {
            const url = "/user/moddata";
            const config = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                },
                body: JSON.stringify(moddata)
            };
            const resp = await fetch(url, config);
            const result = await resp.text();
            return result;
        }

        async function callinfo(id) {
            const response = await fetch("/user/callinfo/" + id);
            const result = await response.json();
            return result;
        }