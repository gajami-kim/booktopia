package com.booktopia.www.service;

import com.booktopia.www.domain.BoardVO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.repository.BoardMapper;
import com.booktopia.www.repository.ReCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final ReCommentMapper reCommentMapper;

    @Override
    public int insert(BoardVO boardVO) {
        log.info("Service boardVO>>>>>{}",boardVO);
        return boardMapper.insert(boardVO);
    }

    @Override
    public int getTotalCount(PagingVO pgvo) {
        return boardMapper.getTotalCount(pgvo);
    }

    @Override
    public List<BoardVO> getList(PagingVO pgvo) {
        return boardMapper.getList(pgvo);
    }

    @Override
    public BoardVO getDetail(long bno) {
        boardMapper.updateReadCnt(bno);
        return boardMapper.getDetail(bno);
    }

    @Override
    public void modify(BoardVO boardVO) {
        boardMapper.modify(boardVO);
    }

    @Override
    public int delete(long bno) {
        boardMapper.delete(bno);
        return 0;
    }

    @Override
    public long getBno() {
        return boardMapper.getBno();
    }

    @Override
    public void updateCommentCnt(long bno) {
        int cnt=1;
        boardMapper.updateCommentCnt(bno,cnt);
    }

    @Override
    public void deleteCommentCnt(Long bvo, long cno) {
        int rcCount = reCommentMapper.rcCount(cno);
        log.info("rccount>>>>>{}",rcCount);
        if(rcCount>0){
            log.info("대댓글 있음{}",rcCount);
            boardMapper.deleteAllCommentCnt(bvo, rcCount);
        } else if(rcCount==0) {
            boardMapper.deleteCommentCnt(bvo);
        }
    }

    @Override
    public List<BoardVO> getCateList(PagingVO pgvo) {
        return boardMapper.getCateList(pgvo);
    }

    @Override
    public int getCateTotalCount(PagingVO pgvo) { return boardMapper.getCateTotalCount(pgvo); }

    @Override
    public int getCateCount() {
        return boardMapper.getCateCount();
    }

    @Override
    public int getCategoryCount(String bCate) {
        return boardMapper.getCategoryCount(bCate);
    }

    @Override
    public void updateHeartCount(long bno) {
        boardMapper.updateHeartCount(bno);
    }

    @Override
    public int getHeartCnt(long bno) {
        return boardMapper.getHeartCnt(bno);
    }

    @Override
    public void deleteHeartCnt(long bno) {
        boardMapper.deleteHeartCnt(bno);
    }
}
