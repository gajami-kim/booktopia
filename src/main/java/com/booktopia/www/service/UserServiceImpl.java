package com.booktopia.www.service;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.domain.DTO.CouponInfoDTO;
import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.domain.QnaVO;
import com.booktopia.www.domain.UserVO;
import com.booktopia.www.repository.*;
import com.mysql.cj.log.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final CouponMapper couponMapper;
    private final CouponUseMapper couponUseMapper;


    // 회원가입
    @Override
    public int joinInsert(UserVO uvo) {
        int isOk = userMapper.joinInsert(uvo);
        int isOk2 = isOk > 0 ? userMapper.insertAuth(uvo.getId()) : 0;
        log.info("joinInsert isOk is {}", isOk);
        if(isOk>0) {
            couponUseMapper.insertUserCoupon(uvo.getId());
        }
        return isOk2;
    }



    @Override
    public int checkId(String id) {
        int isOk = userMapper.checkId(id);
        return isOk;
    }

    @Override
    public String isSocialUser(String id) {
        return userMapper.isSocialUser(id);
    }

    @Override
    public void modifyMyinfo(UserVO uvo) {
        userMapper.modifyMyinfo(uvo);
    }

    @Override
    public void modifyMyinfoWithPwd(UserVO uvo) {
        userMapper.modifyMyinfoWithPwd(uvo);
    }

    @Override
    public String findId(String userName) {
        return userMapper.findId(userName);
    }

    @Override
    public int deleteMyPageUser(String id) {
        return userMapper.deleteMyPageUser(id);
    }

    @Override
    public List<OrderInfoDTO> getPlist(String id) {
        log.info("service in !! >>{} ",id);
        List<OrderInfoDTO> mer_id = orderInfoMapper.selectOrderInfo(id);
        for (OrderInfoDTO payInfo : mer_id) {
            log.info("mer >>> {}",payInfo);
        }
        return mer_id;
    }

    @Override
    public OrderInfoDTO getsublist(String id) {
        OrderInfoDTO sublist =  orderInfoMapper.selectsublist(id);
        return sublist;
    }

    @Override
    public int modifyaddrandphone(UserVO uvo) {
       int isOk =  userMapper.modifyaddrandphone(uvo);
       return isOk;
    }

    @Override
    public List<CouponInfoDTO> getcouList(String id) {
        return couponMapper.getcoulist(id);
    }

    @Override
    public UserVO getcallinfo(String id) {
        return userMapper.getcallinfo(id);
    }


    @Override
    public int findPwCheck(UserVO uvo) {
        return userMapper.findPwCheck(uvo);
    }



}
