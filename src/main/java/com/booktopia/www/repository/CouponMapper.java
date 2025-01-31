package com.booktopia.www.repository;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.domain.CouponUseVO;
import com.booktopia.www.domain.DTO.CouponInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CouponMapper {
    AdCouponVO getCoupon(int couNo);
    List<CouponInfoDTO> getcoulist(String id);
    float getSaleAmount(int couNo);
}
