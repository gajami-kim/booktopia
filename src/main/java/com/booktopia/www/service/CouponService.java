package com.booktopia.www.service;

import com.booktopia.www.domain.AdCouponVO;

public interface CouponService {
    AdCouponVO getCoupon(int couNo);

    float getSaleAmount(int couNo);
}
