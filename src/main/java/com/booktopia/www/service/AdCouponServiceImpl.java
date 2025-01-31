package com.booktopia.www.service;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.domain.DTO.CouponInfoDTO;
import com.booktopia.www.domain.UserVO;
import com.booktopia.www.repository.AdCouponMapper;
import com.booktopia.www.repository.CouponUseMapper;
import com.booktopia.www.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdCouponServiceImpl implements AdCouponService {

    private final AdCouponMapper adCouponMapper;
    private final UserMapper userMapper;
    private final CouponUseMapper couponUseMapper;

    @Override
    public int insert(AdCouponVO adcoupon) {
        // 생성한 쿠폰 입력
        adCouponMapper.insert(adcoupon);

        // 가장 최근에 등록한 쿠폰 번호 추출
        int CouNo = adCouponMapper.getNewCouponNum();

        // user 리스트에서 id만 추출
        List<UserVO> userId = userMapper.getUserId();

        // 아이디별로 새로 생성된 쿠폰 no 입력
        for(int i=0;i<userId.size();i++){
            CouponInfoDTO couponInfoDTO = new CouponInfoDTO();
            couponInfoDTO.setId(userId.get(i).getId());
            couponInfoDTO.setCouNo(CouNo);
            log.info("couponInfoDTO:{}", couponInfoDTO);
            couponUseMapper.newCouponUpdate(couponInfoDTO);
        }
        return 1;
    }
}
