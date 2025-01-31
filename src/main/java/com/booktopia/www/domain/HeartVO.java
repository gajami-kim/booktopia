package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

//좋아요
public class HeartVO {
    private long hno; //좋아요에 부여되는 번호
    private String id; //좋아요 누르는 유저의 id
    private long bno; //좋아요 눌리는 게시물 번호
    private int heartYn; //좋아요 여부

}
