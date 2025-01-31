package com.booktopia.www.repository;

import com.booktopia.www.domain.DTO.OrderInfoDTO;
//import com.booktopia.www.domain.DTO.myPagePayInfoDTO;
import com.booktopia.www.domain.OrderInfoVO;
import com.booktopia.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int insertRegister(OrderInfoDTO oidto);

    OrderInfoDTO getSuccessPayInfo(OrderInfoDTO oidto);

    void insert(OrderInfoVO oivo);

    List<OrderInfoVO> orderList();

    List<OrderInfoDTO> selectOrderInfo(String id);

    OrderInfoDTO selectsublist(String id);

    List<OrderInfoVO> adminOrderList(PagingVO subPgvo);
}
