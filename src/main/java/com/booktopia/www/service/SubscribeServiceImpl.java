package com.booktopia.www.service;

import com.booktopia.www.domain.SubscribeInfoVO;
import com.booktopia.www.repository.SubscribeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscribeServiceImpl implements SubscribeService{

    private final SubscribeMapper subscribeMapper;


    @Override
    public SubscribeInfoVO getPayInfo(int month) {
        return subscribeMapper.getPayInfo(month);
    }

}
