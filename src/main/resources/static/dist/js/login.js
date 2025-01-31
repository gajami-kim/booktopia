console.log("login in");
document.querySelectorAll('.test')[0].addEventListener('click', () => {
            setCookie('login', 'sns', '1');
        });




        function setCookie(cookie_name, value, miuntes) {
            var exdate = new Date();
            exdate.setDate(exdate.getMinutes() + miuntes);
            // 설정 일수만큼 현재시간에 만료값으로 지정
            var cookie_value = escape(value) + ((miuntes == null) ? '' : '; expires=' + exdate.toUTCString());
            document.cookie = cookie_name + '=' + cookie_value;
        }


