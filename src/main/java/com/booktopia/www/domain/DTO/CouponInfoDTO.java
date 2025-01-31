package com.booktopia.www.domain.DTO;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoDTO {

    private String id; //사용자 아이디
    private int couNo; //쿠폰 번호
    private String couUse; // 쿠폰 사용여부
    private String adCouName; // 쿠폰 이름
    private String adCouPeriod; // 쿠폰 유효기간
    private String adCouDate; // 쿠폰 발급 날짜
    private float adCouSale; // 쿠폰 할인율
    private String adCouInfo; // 쿠폰 상세설명

}
