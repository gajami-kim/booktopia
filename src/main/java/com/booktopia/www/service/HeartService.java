package com.booktopia.www.service;

import com.booktopia.www.domain.HeartVO;

import java.util.List;

public interface HeartService {

    HeartVO getUser(String id, long bno);

    void insertHeart(HeartVO hvo);

    HeartVO getBno(long bno);

    HeartVO getUserBno(long bno, String id);

    Integer getHeartYN(long bno,String id);

    int deleteHeart(long bno, String id);

    HeartVO heart1User(String id, long bno);

    void updateHeart(String id, long bno);
}
