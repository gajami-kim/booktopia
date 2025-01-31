package com.booktopia.www.repository;

import com.booktopia.www.domain.SubscribeInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscribeMapper {

    SubscribeInfoVO getPayInfo(int month);
}
