package com.booktopia.www.service;

import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.domain.OrderInfoVO;
import com.booktopia.www.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:secretKey.properties")
public class OrderInfoServiceImpl implements OrderInfoService{
    private final OrderInfoMapper orderInfomapper;
    private final PayMapper payMapper;
    private final UserMapper userMapper;
    private final DeliMapeer deliMapeer;
    private final CouponUseMapper couponUseMapper;


    @Override
    public int insertRegister(OrderInfoDTO oidto) {
        log.info(">>> service in >>> {}", oidto);
        int isOk = orderInfomapper.insertRegister(oidto);
        payMapper.insertPayRegister(oidto);
        deliMapeer.insertDeli(oidto);
        userMapper.updateaddr(oidto);
        if(isOk>0){
            couponUseMapper.insertCouponUse(oidto);
        }

        return isOk;
    }

    @Override
    public OrderInfoDTO getSuccessPayInfo(OrderInfoDTO oidto) {
        return orderInfomapper.getSuccessPayInfo(oidto);
    }


}
