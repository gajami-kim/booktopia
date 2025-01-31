package com.booktopia.www.repository;

import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.PayVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    void insertOrder(PayVO pvo);

    void insertPayRegister(OrderInfoDTO oidto);


    void getSuccessPayInfo(OrderInfoDTO oidto);

    List<PayVO> payList();

    int getTotal();

    List<PayVO> adminPayList(PagingVO subPgvo);
}
