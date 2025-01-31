package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

//관리자 쿠폰
public class AdCouponVO {
    private int couNo; //쿠폰 번호
    private String adCouName; //쿠폰 이름
    private String adCouPeriod; //쿠폰 유효기간
    private String adCouDate; //쿠폰 발급날짜
    private float adCouSale; //할인율
    private String adCouInfo; //쿠폰 정보
}
