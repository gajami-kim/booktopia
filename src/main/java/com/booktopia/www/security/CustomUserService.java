package com.booktopia.www.security;

import com.booktopia.www.domain.UserVO;
import com.booktopia.www.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id)
            throws UsernameNotFoundException {
        UserVO loginUvo = userMapper.comusercheck(id);
        log.info("loginUvo >> {}",loginUvo);
        loginUvo.setAuthList(userMapper.selectAuths(id));

        // 리턴값을 UserDetails로 변경해야함
        return (UserDetails) new AuthUser(loginUvo);
    }


}