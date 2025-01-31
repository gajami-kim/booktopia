package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BooktopiaVO {
    private int btpNO;
    private String id; // 사용자 id
    private String birth; //사용자 생일
    private String gender; //사용자 성별
    private String testAt; // 검사한 시간

    private int btnResult; // 설문점수
}
