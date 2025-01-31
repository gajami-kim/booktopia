package com.booktopia.www.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
// 가격 정보 가져오는 VO
public class PayVO {

    private String tid; // 결제번호
    private String merchantUid; //주문번호
    private String cid; // 가맹점 번호
    private String itemName; //결제할 제품명(구독권이름)
    private int totalAmount; //총 결제금액
    private int saleAmount; //할인금액
    private int couNo; //쿠폰번호
    private String approvedAt; // 결제승인 시각

}
