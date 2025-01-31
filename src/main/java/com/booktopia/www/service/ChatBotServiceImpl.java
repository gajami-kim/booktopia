package com.booktopia.www.service;

import com.booktopia.www.domain.ChatBotVO;
import com.booktopia.www.repository.ChatBotMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatBotServiceImpl implements ChatBotService{
    public final ChatBotMapper chatBotMapper;

    @Override
    public ChatBotVO get(String btn) {
        return chatBotMapper.getcahtBot(btn);
    }
}
