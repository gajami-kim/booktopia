package com.booktopia.www.service;

import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.RecommentVO;
import com.booktopia.www.handler.PagingHandler;

import java.util.List;

public interface ReCommentService {
    int postromment(RecommentVO rvo);

    int deleteCommentFromBoard(long bno);

}
