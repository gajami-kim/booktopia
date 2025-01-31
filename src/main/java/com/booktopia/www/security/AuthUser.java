package com.booktopia.www.security;


import com.booktopia.www.domain.UserVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class AuthUser extends User{

    private UserVO userVO;

    public AuthUser(String id, String pwd, Collection<? extends GrantedAuthority> authorities) {
        super(id, pwd, authorities);
    }

    public AuthUser(UserVO userVO) {
        super(userVO.getId(), userVO.getPwd(), userVO.getAuthList().stream().map
                (authVO -> new SimpleGrantedAuthority(authVO.getRole())).collect(Collectors.toList()));

        this.userVO = userVO;
    }

}