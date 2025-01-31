package com.booktopia.www.repository;

import com.booktopia.www.domain.VoteVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteMapper {
    void insert(VoteVO voteVO);

    VoteVO getUser(String id);

//    void oppinsert(VoteVO voteVO);

//    void agreeUp(int score);
//
//    void oppUp(int score);
}
