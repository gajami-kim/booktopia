package com.booktopia.www.repository;

import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.domain.DeliveryVO;
import com.booktopia.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliMapeer {
    void insertDeli(OrderInfoDTO oidto);

    List<DeliveryVO> getList();

    void updateStaus(String deliUid);


    String getStatus(String merchant);

    void updateScondStaus(String deliUid);

    void updatefinal(String merchantUid);

    int getTotal();

    List<DeliveryVO> adminDelList(PagingVO deliPgvo);
}
