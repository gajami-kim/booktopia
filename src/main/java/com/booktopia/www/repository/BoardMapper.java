package com.booktopia.www.repository;

import com.booktopia.www.domain.BoardVO;
import com.booktopia.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insert(BoardVO boardVO);

    List<BoardVO> getList(PagingVO pgvo);

    int getTotalCount(PagingVO pgvo);

    BoardVO getDetail(long bno);

    void modify(BoardVO boardVO);

    void delete(long bno);

    long getBno();

    void updateReadCnt(long bno);

    void updateCommentCnt(@Param("bno") long bno,@Param("cnt") int cnt);

    void deleteCommentCnt(long bno);

    List<BoardVO> getCateList(PagingVO pgvo);

    int getCateTotalCount(PagingVO pgvo);

    int getCateCount();

    int getCategoryCount(String bCate);

    List<BoardVO> getboarList();

    void deleteAllCommentCnt(@Param("bno")Long bvo, @Param("rccnt")int rcCount);

    int bnoDel(long bno);

    void updateHeartCount(long bno);

    int getHeartCnt(long bno);

    void deleteHeartCnt(long bno);

    int getCount();

    List<BoardVO> adminBoardList(PagingVO boardPgvo);

    int adminDelBoard(long bno);
}
