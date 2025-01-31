package com.booktopia.www.repository;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.domain.DTO.CouponInfoDTO;
import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouponUseMapper {

    // 결제시 쿠폰 사용했을 때 값 '업데이트' 구문
    void insertCouponUse(OrderInfoDTO oidto);

    // 회원가입시 유저한테 쿠폰을 부여
    void insertUserCoupon(String id);

    List<CouponInfoDTO> getCoupon(@Param("id") String id, @Param("couNo") int couNo);

    void newCouponUpdate(CouponInfoDTO couponInfoDTO);
}
