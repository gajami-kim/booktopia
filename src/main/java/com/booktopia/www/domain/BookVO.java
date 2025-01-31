package com.booktopia.www.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 책에 대한 VO
public class BookVO {
    private int bookCode; //책 코드
    private String bookTitle; //책 제목
    private String bookWriter; //책 글쓴이
    private String bookCate; //책 카테고리
    private String bookStory; //책 줄거리
    private String bookImg; // 책 이미지
    private String bookImg2; //디테일에서 쓸 이미지

    private String resultType; //점수 결과
}
