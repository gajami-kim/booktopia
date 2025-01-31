package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponUseVO {
    private String merchant_uid; //결제번호
    private String id; //쿠폰 사용한 사용자 아이디
    private int couNo; //쿠폰 번호
    private String couUse; // 쿠폰 사용여부
}
