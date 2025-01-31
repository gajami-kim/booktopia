package com.booktopia.www.service;

import com.booktopia.www.domain.DTO.MailDTO;
import com.booktopia.www.repository.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SendEmailService {

    @Autowired
    UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "ohe991016@gmail.com";

    public MailDTO createMailAndChangePassword(String email, String id){
        String str = getTempPassword();
        MailDTO dto = new MailDTO();
        dto.setAddress(email);
        dto.setTitle(id+"님의 BOOKTOPIA 임시 비밀번호 발급 안내 이메일입니다.");
        dto.setMessage("안녕하세요. BOOKTOPIA 관리자입니다. 회원님의 임시 비밀번호가 발급되어 전송드립니다. '"+id+"' 님의 임시비밀번호는 [ "+str+" ]" +
                "입니다. 보안을 위해 홈페이지 접속 후 비밀번호를 변경해주시길 바랍니다.");
        updatePassword(str, email);
        return dto;
    }


    public void updatePassword(String str, String email){
        String pw = passwordEncoder.encode(str);
        String id = userMapper.findUserByUserId(email);
        userMapper.updateUserPassword(id,pw);

    }
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDTO mailDto){
        log.info("이메일 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
