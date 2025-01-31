package com.booktopia.www.service;

import com.booktopia.www.domain.BoardVO;
import com.booktopia.www.domain.PagingVO;

import java.util.List;

public interface BoardService {
    int insert(BoardVO boardVO);

    int getTotalCount(PagingVO pgvo);

    List<BoardVO> getList(PagingVO pgvo);

    BoardVO getDetail(long bno);

    void modify(BoardVO boardVO);

    int delete(long bno);

    long getBno();

    void updateCommentCnt(long bno);

    void deleteCommentCnt(Long bvo, long cno);

    List<BoardVO> getCateList(PagingVO pgvo);

    int getCateTotalCount(PagingVO pgvo);

    int getCateCount();

    int getCategoryCount(String bCate);

    void updateHeartCount(long bno);

    int getHeartCnt(long bno);

    void deleteHeartCnt(long bno);
}
