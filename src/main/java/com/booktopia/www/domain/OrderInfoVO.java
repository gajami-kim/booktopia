package com.booktopia.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class OrderInfoVO {
    private String merchantUid; //주문번호
    private String impUid; //아임포트에서 주는 고유번호
    private String id; //주문자아이디
    private String ordName; //주문자이름
    private String ordEmail; // 주문자 이메일
    private String ordPhone; //주문자전화번호
    private String ordAddr; //주문자주소
    private String ordAddrDetail; //주문자상세주소
    private String ordMemo; //주문자배송메모
    private String ordRegdate; //주문시간
}
