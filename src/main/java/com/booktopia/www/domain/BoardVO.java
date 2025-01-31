package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {

    private long bno; //게시글번호
    private String id; //작성자(아이디)
    private String bTitle; //제목
    private String bWriter; //작성자
    private String bContent; //내용
    private String bRegDate; //등록일
    private String bUpDate; //수정일
    private String bReadCnt; //조회수
    private String bCmtCnt; //댓글수
    private String bHeartCnt; //좋아요수
    private String bIsDel; //삭제여부
    private String bCate; //게시글 카테고리
    private String bMainImg; //업로드할 파일 이름

}
