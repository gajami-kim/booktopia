package com.booktopia.www.service;

import com.booktopia.www.domain.CommentVO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.RecommentVO;
import com.booktopia.www.handler.PagingHandler;

public interface CommentService {
    int post(CommentVO cvo);

    PagingHandler getCommentList(int bno, PagingVO pgvo);

    int modify(CommentVO commentVO);

    int deleteComment(long cno);

    long getCommentBno(long cno);
}
