package com.booktopia.www.repository;

import com.booktopia.www.domain.ChatBotVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatBotMapper {

    ChatBotVO getcahtBot(String btn);
}
