package com.booktopia.www.controller;


import com.booktopia.www.domain.QnaVO;
import com.booktopia.www.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qna")
@Slf4j
@RequiredArgsConstructor
public class QnaController {


    private final QnaService qsv;


    @GetMapping("/oneInquiry")
    public void goInquiry(){}

    @PostMapping("/addQna")
    public String addqna(@ModelAttribute QnaVO qnaVO,Model m){
        int isOk = qsv.insertqna(qnaVO);
        m.addAttribute("addqnamsg",isOk);
        return "/user/myPage";
    }

    @GetMapping("/oneInquirylist/{id}")
    @ResponseBody
    public List<QnaVO> getqnaList(@PathVariable("id")String id){
        List<QnaVO> qnaVOList = qsv.getqnalist(id);
        return qnaVOList;
    }
}
