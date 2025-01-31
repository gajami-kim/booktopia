package com.booktopia.www.service;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.repository.CouponMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{
    private final CouponMapper couponMapper;


    @Override
    public AdCouponVO getCoupon(int couNo) {
        return couponMapper.getCoupon(couNo);
    }

    @Override
    public float getSaleAmount(int couNo) {
        return couponMapper.getSaleAmount(couNo);
    }
}
