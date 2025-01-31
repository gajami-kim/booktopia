package com.booktopia.www.service;

import com.booktopia.www.domain.VoteVO;
import com.booktopia.www.repository.SystemInfoMapper;
import com.booktopia.www.repository.VoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteServiceImpl implements VoteService {

    private final VoteMapper voteMapper;
    private final SystemInfoMapper systemInfoMapper;

    @Override
    public void insert(VoteVO voteVO) {

        voteMapper.insert(voteVO);
        log.info(">>> voteVO result 값 >>>>> {}", voteVO.getVoteResult());
        if("찬성".equals(voteVO.getVoteResult())){
            systemInfoMapper.agreeUp();
        } else {
            systemInfoMapper.oppUp();
        }
    }

    @Override
    public VoteVO getUser(String id) {
        return voteMapper.getUser(id);
    }

}
