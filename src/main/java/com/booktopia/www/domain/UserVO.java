package com.booktopia.www.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String id; //사용자 ID
    private String pwd; //사용자 비밀번호
    private String email;
    private String name; //사용자 이름
    private String phone; //사용자 전화번호
    private String address; //사용자 주소
    private String userMemo; //사용자 배송메모
    private String userDel; //탈퇴여부
    private int subNo; //구독정보
    private String userReg; //가입날짜
    private String userUp; //수정날짜
    private String userType; //가입유형
    private String accessToken;
    private List<AuthVO> authList;//권한 목록

}
