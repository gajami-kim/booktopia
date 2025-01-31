package com.booktopia.www.service;

import com.booktopia.www.domain.CommentVO;
import com.booktopia.www.domain.DTO.CommentDTO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.RecommentVO;
import com.booktopia.www.handler.PagingHandler;
import com.booktopia.www.repository.CommentMapper;
import com.booktopia.www.repository.ReCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;
    private final ReCommentMapper reCommentMapper;

    @Override
    public int post(CommentVO cvo) {
        return commentMapper.post(cvo);
    }

    @Override
    public PagingHandler getCommentList(int bno, PagingVO pgvo) {
        //totalCount
        int totalCount = commentMapper.getSelectOneComment(bno);
        List<CommentVO> clist = commentMapper.getCommentList(bno, pgvo);
        List<CommentDTO> cdtoList = new ArrayList<>();
        if(clist.size()!=0){
            for(CommentVO cvo : clist){
                CommentDTO cdto = new CommentDTO();
                long cno = cvo.getCno();
                List<RecommentVO> rlist = reCommentMapper.getReCommentList(cno, pgvo);
                cdto.setCvo(cvo);
                cdto.setRclist(rlist);
                cdtoList.add(cdto);
                log.info("cno>>>>{}",cno);
                log.info("rlist>>>>{}",rlist);
            }
        }
        log.info("clist>>>>{}",clist);
        log.info("cdtoList>>>>{}",cdtoList);
        PagingHandler ph = new PagingHandler(pgvo, totalCount, (List<CommentDTO>) cdtoList);
        return ph;
    }

    @Override
    public int modify(CommentVO commentVO) {
        return commentMapper.modify(commentVO);
    }

    @Override
    public int deleteComment(long cno) {
        int isOk = reCommentMapper.deleteReComment(cno);
        if(isOk>=0){
            commentMapper.deleteComment(cno);
            isOk=1;
        }
        return isOk;
    }

    @Override
    public long getCommentBno(long cno) {
        return commentMapper.getCommentBno(cno);
    }

}
