package com.booktopia.www.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
// 구독정보 VO
public class SubscribeInfoVO {
    private int shipNo; // 구독 개월수를 숫자로 기본키 용도
    private int subInMonth; // 구독개월 수
    private int subInPrice; // 구독 개월수에 따른 가격
}
