package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryVO {
    private int deliNo;
    private String merchantUid; //주문번호
    private String id; //주문자 id
    private String itemName; //주문상품
    private String deliDate; //발송일자
    private String deliStatus; // 주문상태 | '배송준비중' / '배송중' / '배송완료'

}
