package com.booktopia.www.controller;

import com.booktopia.www.domain.ChatBotVO;
import com.booktopia.www.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatbot/*")
public class ChatBotController {

    public final ChatBotService chatBotService;

    @PostMapping("/get")
    @ResponseBody
    public String getChatBot(@RequestBody String btn, Model m){
        ChatBotVO chatBotVO = chatBotService.get(btn);
        return "1";
    }
}
