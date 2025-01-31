package com.booktopia.www.service;

import com.booktopia.www.domain.CommentVO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.RecommentVO;
import com.booktopia.www.handler.PagingHandler;
import com.booktopia.www.repository.CommentMapper;
import com.booktopia.www.repository.ReCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReCommentServiceImpl implements ReCommentService{
    private final ReCommentMapper reCommentMapper;
    private final CommentMapper commentMapper;

    @Override
    public int postromment(RecommentVO rvo) {
        return reCommentMapper.postromment(rvo);
    }

    @Override
    public int deleteCommentFromBoard(long bno) {
        int isOk = reCommentMapper.deleteFromBoard(bno);
        if(isOk>0) {
            commentMapper.deleteCommentFromBoard(bno);
        }
        return isOk;
    }

}
