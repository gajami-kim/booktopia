package com.booktopia.www.service;

import com.booktopia.www.domain.QnaVO;

import java.util.List;

public interface QnaService {
    
    int insertqna(QnaVO qnaVO);

    List<QnaVO> getqnalist(String id);
}
