package com.booktopia.www.controller;

import com.booktopia.www.domain.DTO.CouponInfoDTO;
import com.booktopia.www.repository.CouponUseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/coupon/*")
public class CouponController {
    private final CouponUseMapper couponUseMapper;

    @PostMapping("/sale/{couNo}/{id}")
    @ResponseBody
    public List<CouponInfoDTO> couponInfo(@PathVariable("couNo") int couNo, @PathVariable("id")String id) {
        List<CouponInfoDTO> cidto = couponUseMapper.getCoupon(id,couNo);
        return cidto;
    }
}
