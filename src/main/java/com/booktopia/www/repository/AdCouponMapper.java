package com.booktopia.www.repository;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdCouponMapper {

    int getCount();

    // 쿠폰 리스트 가져오기..
    List<AdCouponVO> getList(PagingVO couponPvo);

    // 쿠폰 새로 생성
    int insert(AdCouponVO adcoupon);

    List<AdCouponVO> joingetList();

    int getNewCouponNum();

    List<AdCouponVO> getCouponList();
}
