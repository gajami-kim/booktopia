package com.booktopia.www.service;

import com.booktopia.www.domain.VoteVO;

public interface VoteService {
    void insert(VoteVO voteVO);

    VoteVO getUser(String id);
}
