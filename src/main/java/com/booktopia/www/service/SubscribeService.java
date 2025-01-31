package com.booktopia.www.service;

import com.booktopia.www.domain.SubscribeInfoVO;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface SubscribeService {

    SubscribeInfoVO getPayInfo(int month);

}
