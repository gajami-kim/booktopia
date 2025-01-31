package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

//챗봇
public class ChatBotVO {
    private String chatNo; //질문에 부여되는 번호 1,2,...4~5?
    private String chatAns; //질문에 부여되는 답변 1-답변 2-답변 ..
    private String chatType;
}
