# 북토피아🌠
1:1 퍼스널 책 제공 서비스

## 🖥프로젝트 소개
독서를 즐기고 싶지만, 나의 독서 취향이나 스타일에 대해 모르는 분들을 위해 <br>
1:1 퍼스널 서비스를 제공합니다.<br>
북토피아 검사(취향검사)를 통해 사용자의 취향에 맞는 책을 선정하도록 만들었습니다.<br>
__SpringBoot MVC패턴을 기반으로 작업되었으며__ DB생성, 유저관리, 게시판 관리(기능), 구매 기능 등 <br>
백엔드와 프론트엔드에서 다양한 기능을 구현할 수 있도록 제작된 사이트입니다.

## ⏲개발 기간 및 참여인원
* 24.05.17 ~ 24.06.24
* 가명선, __김정온__, 오하은, 윤지명
## 
### 👩🏻‍💻 기여한 부분
__1. [결제 기능](#결제-기능)__ <br>
   <br> - [결제하기](#--결제하기) <br>
   <br> - [쿠폰 사용](#--쿠폰-사용) <br><br>
__2. [게시판 CRUD](#게시판-crud)__ <br>
   <br> - [게시글 등록 / 수정 / 삭제](#--게시글-등록--수정--삭제)<br>
   <br> - [파일 등록](#--파일-등록)<br>
   <br> - [마크다운 적용](#--마크다운-적용)<br><br>
__3. [게시글 좋아요](#게시글-좋아요)__ <br><br>
__4. [댓글](#댓글)__ <br>
   <br> - [댓글](#--댓글)<br>
   <br> - [대댓글](#--대댓글)
##
## 결제 기능
#### - 결제하기
- PG사와 버튼 클릭에 의한 CSS 변경을 위해 결제 수단을 객체로 저장하였습니다.

해당 방법을 이용해 아임포트 request 데이터를 보낼 때 pg사(결제방법)을 함께 전송합니다.
현재 payco는 결제를 제한하였습니다.
![북토피아 pg 저장](https://github.com/user-attachments/assets/f1973ec4-faa1-46c2-9f44-681dc28278a0)


결제 금액을 검증한 뒤 요청 금액과 DB 속 저장된 구독권 금액이 같으면 결제를 진행합니다.
결제 번호(merchant_uid), 구독권 이름(item_name), 총 결제금액(total_amount), 할인금액(sale_amount), 사용한 쿠폰 번호(cou_no), 결제시간(approve_at)을 저장합니다.
![북토피아 결제DB](https://github.com/user-attachments/assets/98ef8851-7f9f-4844-bf6c-9b1c76651473)

결제가 정보가 저장되면 주문번호(imp_uid)를 포함한 주문자 정보를 추가적으로 저장합니다. 
![북토피아 주문DB](https://github.com/user-attachments/assets/f904b4fc-f5d5-4369-ae7d-87aa623d951d)

여기서 저장된 주문번호는 차후 결제 취소, 환불 등에 사용될 수 있습니다.


#### - 쿠폰 사용
어드민에서 발행한 쿠폰을 사용자가 사용할 수 있습니다.
![북토피아 쿠폰 선택](https://github.com/user-attachments/assets/3370eeb1-8195-4a94-806d-12d1e436d6ed)
![북토피아 쿠폰적용 view](https://github.com/user-attachments/assets/fceb318c-374f-4533-9cb3-797c0a16a215)

쿠폰 발행 및 등록 로직이 짜여지지 않아 쿠폰 이름을 미리 DB에 저장해두었습니다.

쿠폰 정보를 coupons 객체에 저장합니다.
select를 이용하여 쿠폰을 선택하면 해당 쿠폰정보와 할인금액, 할인이 적용된 총 금액을 출력합니다.
![스크린샷 2025-02-03 225201](https://github.com/user-attachments/assets/ed3d8710-257b-42e7-a9f3-71be93f29a61)

쿠폰을 선택한 후 할인된 금액을 반환하여 결제시 아임포트에 전송되는 데이터에 적용시킵니다.
![스크린샷 2025-02-03 225131](https://github.com/user-attachments/assets/6d7c8d7d-e282-4a0b-b951-41e9840b74e4)

#
## 게시판 CRUD
####  - 게시글 등록 / 수정 / 삭제
toast ui에서 제공하는 editor를 이용해 북토피아🌠
1:1 퍼스널 책 제공 서비스

## 🖥프로젝트 소개
독서를 즐기고 싶지만, 나의 독서 취향이나 스타일에 대해 모르는 분들을 위해 <br>
1:1 퍼스널 서비스를 제공합니다.<br>
북토피아 검사(취향검사)를 통해 사용자의 취향에 맞는 책을 선정하도록 만들었습니다.<br>
__SpringBoot MVC패턴을 기반으로 작업되었으며__ DB생성, 유저관리, 게시판 관리(기능), 구매 기능 등 <br>
백엔드와 프론트엔드에서 다양한 기능을 구현할 수 있도록 제작된 사이트입니다.

## ⏲개발 기간 및 참여인원
* 24.05.17 ~ 24.06.24
* 가명선, __김정온__, 오하은, 윤지명
## 
### 👩🏻‍💻 기여한 부분
__1. [결제 기능](#결제-기능)__ <br>
   <br> - [결제하기](#--결제하기) <br>
   <br> - [쿠폰 사용](#--쿠폰-사용) <br><br>
__2. [게시판 CRUD](#게시판-crud)__ <br>
   <br> - [게시글 등록 / 수정 / 삭제](#--게시글-등록--수정--삭제)<br>
   <br> - [파일 등록](#--파일-등록)<br>
   <br> - [마크다운 적용](#--마크다운-적용)<br><br>
__3. [게시글 좋아요](#게시글-좋아요)__ <br><br>
__4. [댓글](#댓글)__ <br>
   <br> - [댓글](#--댓글)<br>
   <br> - [대댓글](#--대댓글)
##
## 결제 기능
#### - 결제하기
- PG사와 버튼 클릭에 의한 CSS 변경을 위해 결제 수단을 객체로 저장하였습니다.

해당 방법을 이용해 아임포트 request 데이터를 보낼 때 pg사(결제방법)을 함께 전송합니다.
현재 payco는 결제를 제한하였습니다.
![북토피아 pg 저장](https://github.com/user-attachments/assets/f1973ec4-faa1-46c2-9f44-681dc28278a0)


결제 금액을 검증한 뒤 요청 금액과 DB 속 저장된 구독권 금액이 같으면 결제를 진행합니다.
결제 번호(merchant_uid), 구독권 이름(item_name), 총 결제금액(total_amount), 할인금액(sale_amount), 사용한 쿠폰 번호(cou_no), 결제시간(approve_at)을 저장합니다.
![북토피아 결제DB](https://github.com/user-attachments/assets/98ef8851-7f9f-4844-bf6c-9b1c76651473)

결제가 정보가 저장되면 주문번호(imp_uid)를 포함한 주문자 정보를 추가적으로 저장합니다. 
![북토피아 주문DB](https://github.com/user-attachments/assets/f904b4fc-f5d5-4369-ae7d-87aa623d951d)

여기서 저장된 주문번호는 차후 결제 취소, 환불 등에 사용될 수 있습니다.


#### - 쿠폰 사용
어드민에서 발행한 쿠폰을 사용자가 사용할 수 있습니다.
![북토피아 쿠폰 선택](https://github.com/user-attachments/assets/3370eeb1-8195-4a94-806d-12d1e436d6ed)
![북토피아 쿠폰적용 view](https://github.com/user-attachments/assets/fceb318c-374f-4533-9cb3-797c0a16a215)

쿠폰 발행 및 등록 로직이 짜여지지 않아 쿠폰 이름을 미리 DB에 저장해두었습니다.

쿠폰 정보를 coupons 객체에 저장합니다.
select를 이용하여 쿠폰을 선택하면 해당 쿠폰정보와 할인금액, 할인이 적용된 총 금액을 출력합니다.
![스크린샷 2025-02-03 225201](https://github.com/user-attachments/assets/ed3d8710-257b-42e7-a9f3-71be93f29a61)

쿠폰을 선택한 후 할인된 금액을 반환하여 결제시 아임포트에 전송되는 데이터에 적용시킵니다.
![스크린샷 2025-02-03 225131](https://github.com/user-attachments/assets/6d7c8d7d-e282-4a0b-b951-41e9840b74e4)

#
## 게시판 CRUD
####  - 게시글 등록 / 수정 / 삭제
Toast ui의 Editor를 활용하여 글쓰기 형식을 지정하고 필요한 기능을 추가하였습니다.
또한, hooks를 이용해 이미지 업로드 시 파일 경로를 전달하고, 이를 출력할 수 있도록 구현하였습니다.
![북토피아 toast js](https://github.com/user-attachments/assets/9422a12f-5129-4bc9-923e-10d526d7a337)

게시판 선택을 위한 select 태그는 직접 디자인하여 제작하였으며 작성자, 제목, 본문을 모두 입력하면
유형석 검사 변수 isValid를 true로 변경하도록 설정하였습니다.
유효성 검사 통과 시 게시글 데이터는 서버로 전송되어 DB에 저장 및 업로드 할 수 있습니다.
![북토피아 글쓰기 셀렉트문](https://github.com/user-attachments/assets/e73639a7-14a1-4de6-bfbf-66a58e0015e5)
![북토피아 toast js 데이터](https://github.com/user-attachments/assets/2454eeb5-2512-4626-a828-07ede05476db)

작성자는 소셜 유저와 일반 유저를 구분하여 소셜 유저의 경우 이메일을, 일반 유저는 아이디를 작성자로 저장하도록 하였습니다.
![북토피아 toast js 유효성검사](https://github.com/user-attachments/assets/0f4530f7-332a-434e-ad9a-2a46b335e054)

게시글 수정 및 삭제가 가능하도록 하였습니다.
수정 시 : 게시글 데이터를 새로 구성하여 DB가 업데이트하도록 설정
삭제 시 : 게시글 번호(bno)를 기준으로 게시글, 댓글, 대댓글, 하트 모두 삭제되도록 구현
![북토피아 게시글삭제](https://github.com/user-attachments/assets/44b1694f-217e-402f-ab92-25c96560ba29)


#### - 파일 등록
Toast ui의 hooks를 이용하여 전달받은 파일 경로를 처리하는 컨트롤러 입니다.
폴더는 C드라이브 > booktopia > 날짜순으로 지정되어있습니다.
만약 폴더가 없는 경우 mkdirs() 명령어를 이용하여 폴더를 생성하고, 있는 경우는 오늘 날짜를 찾아 파일을 저장합니다.
uuid를 이용하여 중복되는 파일 이름이 생성되지 않도록 방지하였습니다.
![북토피아 파일 업로드 컨트롤러](https://github.com/user-attachments/assets/e2da8c48-66b8-4ad7-81cf-e0833981b4b1)
![북토피아 파일 저장](https://github.com/user-attachments/assets/bc847e1e-391a-4ec7-9121-31ec3f5b4041)
![북토피아 파일 db](https://github.com/user-attachments/assets/ca82b516-a4ce-4180-8daf-ec29b20469e4)

파일 출력의 경우 전달 받은 파일 이름을 이용하여 파일을 찾고 파일이 있는 경우
byte[]로 변환한 이미지를 hook으로 다시 반환하여 이미지를 출력할 수 있도록 하였습니다.
![북토피아 파일 출력](https://github.com/user-attachments/assets/7a29c683-b926-40b8-bef8-7122ecf212d9)


#### - 마크다운 적용
![북토피아 마크다운](https://github.com/user-attachments/assets/eb23c68c-02a8-4c47-b8ac-fda6c9a192d4)

#
## 게시글 좋아요
![북토피아 하트](https://github.com/user-attachments/assets/c8bbc21e-552f-4327-a4a2-8be0897d9442)
![북토피아 하트 컨트롤러](https://github.com/user-attachments/assets/625debff-8c32-4d4f-bcbd-403987be7f0d)

#
## 댓글
#### - 댓글 
![댓글](https://github.com/user-attachments/assets/475cec5b-9321-4ffd-9fc3-588bf0512f20)

#### - 대댓글
![대댓글](https://github.com/user-attachments/assets/55ef4257-3d71-4fef-8241-49383cb4c2a0)

### [위로 올라가기](#프로젝트-소개)
