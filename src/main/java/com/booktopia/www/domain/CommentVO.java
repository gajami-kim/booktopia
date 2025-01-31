package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {

    private long cno; //댓글번호
    private long bno; //게시글번호
    private String cWriter; //댓글작성자(익명)
    private String cContent; //댓글내용
    private String cRegDate; //댓글등록일
    private String cType; //게시글유형 커뮤C, 찬반V

}
