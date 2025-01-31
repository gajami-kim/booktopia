package com.booktopia.www.repository;

import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.QnaVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaMapper {

    int insertqna(QnaVO qnaVO);

    List<QnaVO> getqnalist(String id);

    int qnaCount();

    List<QnaVO> getList(PagingVO qnaPgvo);

//    String oneUserList(int qnaNum);


    void updateAnswer(@Param("qnaAnswer") String qnaAnswer, @Param("qnaNum") int qnaNum);

    void updateStatus(int qnaNum);

    String getStatus(int qnaNum);

    List<QnaVO> oneUserList(int qnaNum);
}
