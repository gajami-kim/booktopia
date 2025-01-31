
let i=1;
let uid = '';
let merchant_uid = 'payment_'+new Date().getTime()+i;
let item_name = '북토피아 '+payName+'개월 구독권';
const regPhone = new RegExp(/[09]/);
const amountInput = document.querySelector('.amountInput').value;
var payId = document.getElementById("realId");
var addrone = document.getElementById("addrInput");
var addrtwo = document.getElementById("addrDetailInput");
var phonecall = document.getElementById("ordPhone");


callinfo(payId.innerText).then(result => {
    phonecall.value = result.phone;
    addrone.value = result.address.substring(0, result.address.indexOf("/"));
    addrtwo.value = result.address.substring(result.address.indexOf("/") + 1);
});

const kakaoPayBtn = document.getElementById('kakaoPayBtn');
const tossBtn = document.getElementById('tossBtn');
const paycoBtn = document.getElementById('paycoBtn');
const kgBtn = document.getElementById('kgBtn');
const tossCss = document.querySelector('.tossM');
const kakaoCss = document.querySelector('.kakaoPayM');
const paycoCss = document.querySelector('.paycoM');
const kgCss = document.querySelector('.kgM');

let pg;
/*css 변경하는 이벤트*/
document.addEventListener('click',(e)=>{
    if(e.target.id==='kakaoPayBtn'){
        changeCss(kakaoCss);
        changeNonCss(tossCss,paycoCss,kgCss);
        pg='kakaopay.TC0ONETIME';
        request_pay(pg);
    } else if(e.target.id==='tossBtn'){
        changeCss(tossCss);
        changeNonCss(kakaoCss,paycoCss,kgCss);
        pg='tosspayments.iamporttest_3';
        request_pay(pg);
    } else if(e.target.id==='paycoBtn') {
        changeCss(paycoCss);
        changeNonCss(kakaoCss,tossCss,kgCss);
        alert('현재 사용할 수 없는 결제 수단입니다.');
    } else if(e.target.id==='kgBtn'){
        changeCss(kgCss);
        changeNonCss(kakaoCss,paycoCss,tossCss);
        pg='html5_inicis.INIpayTest';
        request_pay(pg);
    }
})


function changeCss(payM){
    payM.style.cssText="border:2px solid #a7cdff; box-shadow:0px 0px 12px gainsboro; font-weight:bold;";
}
function changeNonCss(payM1,payM2,payM3){
    payM1.style.cssText="border:1px solid gainsboro; padding10px; border-radius:10px; font-weight:none;" +
        "margin-top:14px; text-align:center; line-height:23px; width:437px; box-shadow:none";
    payM2.style.cssText="border:1px solid gainsboro; padding10px; border-radius:10px; font-weight:none;" +
        "margin-top:14px; text-align:center; line-height:23px; width:437px; box-shadow:none";
    payM3.style.cssText="border:1px solid gainsboro; padding10px; border-radius:10px; font-weight:none;" +
        "margin-top:14px; text-align:center; line-height:23px; width:437px; box-shadow:none";
}

function request_pay(pg){
    let ordaddr = document.getElementById('addrInput').value;
    let ordaddrdetail = document.getElementById('addrDetailInput').value;
    let ordPhone = document.getElementById('ordPhone').value;
    const coupon = $('select#coupon').val();


    if(ordPhone==null||ordPhone===''){
        alert("전화번호를 입력해주세요.");
        document.getElementById('ordPhone').focus();
    } else if(!regPhone.test(ordPhone)){
        document.getElementById('ordPhone').value='';
        document.getElementById('ordPhone').focus();
        alert("숫자만 입력해주세요.");
    } else if(ordaddr==null||ordaddr===''||ordaddrdetail==null||ordaddrdetail===''){
        alert("주소를 입력해주세요.");
        document.getElementById('ordaddr').focus();
    } else {
        if(coupon!=='choiceCoupon'){
            amount = discountAmount(amount);
        }

        const IMP = window.IMP;
        IMP.init("imp42245168")
        IMP.request_pay(
            {
                pg:pg,
                pay_method:'card',
                merchant_uid : merchant_uid , //주문번호
                name: item_name,
                amount:amount,
                buyer_name:ordName,
                buyer_email:ordEmail,
                buyer_tel:ordPhone,
                buyer_addr:ordaddr+"/"+ordaddrdetail,
                buyer_addrDetail : ordaddrdetail,
            },
            function (rsp){
                // callback
                // res.imp_uid 값으로 결제 단건조회 API 호출해서 결제 결과 판단
                if(rsp.success){
                    let registerData;
                    const saleRates = {
                        "신규가입 구독권 10% 할인": 0.1,
                        "신규회원 1개월 구독권 99% 할인": 0.99,
                        "6월 내 구독권 결제 시, 10% 할인": 0.1,
                        "북토피아 창립기념 쿠폰 30% 할인": 0.3,
                    }

                    const saleAmount = coupon in saleRates ? amountInput * saleRates[coupon] : 0;
                    const couNo = coupon in saleRates ? couNo : 0;

                    registerData = {
                        id:ordId,
                        impUid: rsp.imp_uid,
                        merchantUid: merchant_uid,
                        ordEmail:ordEmail,
                        ordName:ordName,
                        ordPhone: ordPhone,
                        ordAddr:ordaddr+"/"+ordaddrdetail,
                        itemName:item_name,
                        totalAmount: amount,
                        saleAmount: saleAmount,
                        couNo: couNo,
                        pg_tid:rsp.pg_tid,
                    };

                    postRegisterSuccess(registerData).then(result=>{
                        if(result === '1'){
                            let data ={
                                id:ordId,
                                impUid : rsp.imp_uid,
                                merchantUid: merchant_uid,
                                ordEmail:ordEmail,
                                buyerName : ordName,
                                ordPhone:ordPhone,
                                address: ordaddr,
                                name :item_name,
                                paidAmount : amount,
                            };
                            getToken();
                            // 결제금액 검증
                            if (registerData.totalAmount == data.paidAmount){
                                postStorePaySuccess(data).then(result=>{
                                    window.location.href = "/pay/done/"+result.merchantUid;
                                });
                            } else {
                                alert("결제 실패")
                            }
                        } else {
                            console.log("data 안들어옴")
                        }
                    } )
                } else {
                    alert('결제를 취소하였습니다.');
                }
            }
        );
        i++;
    }
}
async function postRegisterSuccess(registerData){
    try {
        const url = "/pay/savePayinfo";
        const config = {
            method : 'post',
            headers : {
                'content-type':'application/json; charset =utf-8'
            },
            body :JSON.stringify(registerData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error){
        console.log(error);
    }
}
async function postStorePaySuccess(data){
    try {
        const url = "/pay/done";
        const config ={
            method: 'POST',
            headers : {
                'content-type':'application/json; charset =utf-8'
            },
            body: JSON.stringify(data)
        };
        const resp = await fetch(url, config);
        const result = await resp.json();
        return result;
    }catch (error){
        console.log("error" + error);
    }
}
// token 가져오는...
async function getToken(){
    try{
        const resp = await fetch('/pay/getToken', {
            method : 'POST'
        });
        const result = await resp.json();
        return result;
    }catch (error){
        console.log(error);
    }
}
let salePercent=0;
let couNo=0;
function discountAmount(amount){
    const coupon = $('select#coupon').val();

    const coupons = {
        '신규가입 구독권 10% 할인': { discountRate: 0.1, couNo: 2 },
        '1년 이상 누적 구독시, 구독권 50% 할인 쿠폰': { discountRate: 0.5, couNo: 1 },
        '신규회원 1개월 구독권 99% 할인': { discountRate: 0.99, couNo: 3 },
        '6월 내 구독권 결제 시, 10% 할인': { discountRate: 0.1, couNo: 4 },
        '북토피아 창립기념 쿠폰 30% 할인': { discountRate: 0.3, couNo: 5 }
    };

    const selectedCoupon = coupons[coupon];

    if (!selectedCoupon) {
        return amount; // 쿠폰이 유효하지 않은 경우 할인 없이 원래 금액 반환
    }

    const discount = amount * selectedCoupon.discountRate;
    return amount - discount;
}

document.getElementById('coupon').addEventListener('change',()=>{
    const couponName = $('select#coupon').val();
    const payAmount = document.querySelector('.priceDiv').value;
    const discountDiv = document.querySelector('.discountAmount');
    const amountDiv = document.querySelector('.amountDiv');

    const coupons  = {
        '신규가입 구독권 10% 할인' : {couNo:2, discountRate:0.1},
        '신규회원 1개월 구독권 99% 할인' : {couNo:3, discountRate:0.99},
        '6월 내 구독권 결제 시, 10% 할인' : {couNo:4, discountRate:0.1},
        '북토피아 창립기념 쿠폰 30% 할인' : {couNo:2, discountRate:0.3},
        '1년 이상 누적 구독시, 구독권 50% 할인' : {couNo:null, isInvalid: true},
        'choiceCoupon' : {couNo:null, discountRate:0}
    }

    const selectedCoupon = coupons[couponName];

    if (!selectedCoupon) {
        alert('유효하지 않은 쿠폰입니다.');
        return;
    }

    if (selectedCoupon.isInvalid) {
        alert('현재 사용할 수 없는 쿠폰입니다.');
        $('#coupon').val('choiceCoupon').prop('selected', true);
        discountDiv.textContent = '0원';
        amountDiv.textContent = payAmount + '원';
        return;
    }

    discountCoupon(selectedCoupon.couNo, ordId)
        .then(result => {
            result.forEach(item => {
                if (item.couUse === 'N') {
                    const discountAmount = payAmount * selectedCoupon.discountRate;
                    discountDiv.textContent = discountAmount + '원';
                    amountDiv.textContent = (payAmount - discountAmount) + '원';
                } else {
                    alert("이미 사용한 쿠폰입니다.");
                    $('#coupon').val('choiceCoupon').prop('selected', true);
                    discountDiv.textContent = '0원';
                    amountDiv.textContent = payAmount + '원';
                }
            });
        });
})

async function discountCoupon(couNo,id){
    try{
        const url = "/coupon/sale/"+couNo+"/"+id;
        const config = {
            method:"POST",
            headers:{
                'content-type':'application/json; charset=UTF-8'
            }
        }
        const resp = await fetch(url,config);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log("discount error"+error);
    }
}

async function callinfo(id) {
    const response = await fetch("/user/callinfo/" + id);
    const result = await response.json();
    return result;
}