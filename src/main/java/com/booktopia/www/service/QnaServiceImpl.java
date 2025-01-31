package com.booktopia.www.service;

import com.booktopia.www.domain.QnaVO;
import com.booktopia.www.repository.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaServiceImpl implements QnaService{

    private final QnaMapper qnaMapper;


    @Override
    public int insertqna(QnaVO qnaVO) {
        int isOk = qnaMapper.insertqna(qnaVO);
        return isOk;
    }

    @Override
    public List<QnaVO> getqnalist(String id) {
        log.info("id >> {} ",id);
        return qnaMapper.getqnalist(id);
    }
}
