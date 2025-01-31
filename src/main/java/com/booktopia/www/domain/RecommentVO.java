package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecommentVO {

    private int rcCno; //대댓글번호
    private long cno; //댓글번호
    private int bno; //게시글번호
    private String rcWriter; //대댓작성자
    private String rcContent; //대댓내용
    private String rcRegDate; //대댓등록일

}
