package com.booktopia.www.domain;

public class SubscribeVO {

    private String subNO; //구독 개월 수 알려주는 번호
    private String id; //사용자 id
    private int subStatus; // 구독 상태 해지 / 구독중 / 만료
    private String subStart; //구독 시작 기간
    private String subEnd; //구독 종료 기간
}
