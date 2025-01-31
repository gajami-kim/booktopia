package com.booktopia.www.controller;

import com.booktopia.www.repository.SystemInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/system/*")
public class SystemInfoController {
    public final SystemInfoMapper systemInfoMapper;

    @GetMapping("/getScore")
    public String getScore (Model m){
        int isOk = systemInfoMapper.getScore();
        m.addAttribute("score", isOk);
        return "/community/communityMain";
    }

}
