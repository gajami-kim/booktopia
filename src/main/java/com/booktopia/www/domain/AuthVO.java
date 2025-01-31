package com.booktopia.www.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthVO {
    private String id; //사용자 id
    private String role; //사용자 권한
}
