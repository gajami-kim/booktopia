package com.booktopia.www.repository;

import com.booktopia.www.domain.HeartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface HeartMapper {
    HeartVO getUser(@Param("id")String id, @Param("bno")long bno);

    void insertHeart(HeartVO hvo);

    HeartVO getBno(long bno);

    HeartVO getUSerBno(long bno, String id);

    Integer getHeartYN(@Param("bno") long bno, @Param("id") String id);

    int deleteHeart(@Param("bno") long bno,@Param("id") String id);

    int getcount(long bno);

    void adminDelHeart(long bno);

    HeartVO heart1User(@Param("id") String id, @Param("bno") long bno);

    void updateHeart(@Param("id") String id,@Param("bno") long bno);
}
