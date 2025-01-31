package com.booktopia.www.controller;

import com.booktopia.www.domain.DeliveryVO;
import com.booktopia.www.repository.DeliMapeer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage/*")
public class MyPageController {

    private final DeliMapeer deliMapeer;

    @GetMapping("/modify")
    public void modify() {}

    @GetMapping("/changeaddr")
    public void changeaddr() {}

    @GetMapping("/changeaddrsocial")
    public void changeaddrSocial() {}

    @GetMapping("/couponlist")
    public void couponlist() {}

    @GetMapping("/subinfo")
    public void subinfo() {}

    @GetMapping("/qnaresult")
    public void qnaresult(){}

    @GetMapping("/payinfo")
    public void payinfo(Model model) {
        List<DeliveryVO> deliveryList = deliMapeer.getList();

        model.addAttribute("deliveryList", deliveryList);
    }

    @PostMapping("/deliSuccess")
    @ResponseBody
    public String deliSuccess(@RequestBody String merchantUid){
        deliMapeer.updatefinal(merchantUid);
        return "1";
    }
}
