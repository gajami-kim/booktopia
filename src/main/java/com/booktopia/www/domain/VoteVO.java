package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VoteVO {

    private String id; //투표할 유저 아이디
    private String voteResult; //투표결과 (찬성 / 반대)


}
