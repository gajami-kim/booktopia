package com.booktopia.www.service;

import com.booktopia.www.domain.HeartVO;
import com.booktopia.www.repository.BoardMapper;
import com.booktopia.www.repository.HeartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartMapper heartMapper;
    private final BoardMapper boardMapper;

    @Override
    public HeartVO getUser(String id, long bno) {
        return heartMapper.getUser(id, bno);
    }

    @Override
    public void insertHeart(HeartVO hvo) {
        heartMapper.insertHeart(hvo);
    }

    @Override
    public HeartVO getBno(long bno) {
        return heartMapper.getBno(bno);
    }

    @Override
    public HeartVO getUserBno(long bno, String id) {
        return heartMapper.getUSerBno(bno, id);
    }

    @Override
    public Integer getHeartYN(long bno, String id) {
        return heartMapper.getHeartYN(bno,id);
    }

    @Override
    public int deleteHeart(long bno, String id) {
        return heartMapper.deleteHeart(bno,id);
    }

    @Override
    public HeartVO heart1User(String id, long bno) {
        return heartMapper.heart1User(id, bno);
    }

    @Override
    public void updateHeart(String id, long bno) {
        heartMapper.updateHeart(id,bno);
    }


}
