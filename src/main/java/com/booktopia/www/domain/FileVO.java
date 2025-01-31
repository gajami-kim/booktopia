package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

//파일
public class FileVO {
    private String uuid; //uuid
    private String saveDir; //저장경로
    private String fileName; //파일이름
    private long fileSize; //파일사이즈
    private int fileType; //파일타입 -> 책 0 / 그외 1
    private int bookCode; //책 코드
}
