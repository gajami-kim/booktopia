package com.booktopia.www.controller;

import com.booktopia.www.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/subscribe/*")
@Controller
public class SubscribeController {
    private final SubscribeService ssv;

    @GetMapping("/info")
    public void info(){}
}
