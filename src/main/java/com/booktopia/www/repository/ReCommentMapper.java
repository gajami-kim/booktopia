package com.booktopia.www.repository;

import com.booktopia.www.domain.DTO.CommentDTO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.RecommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReCommentMapper {
    int postromment(RecommentVO rvo);

    List<RecommentVO> getReCommentList(@Param("cno") long cno, @Param("pgvo") PagingVO pgvo);

    int deleteFromBoard(long bno);

    int deleteReComment(long cno);

    int rcCount(long cno);

    int getreCommentCount();

    List<CommentDTO> adminreCommtneList();

    void adminDelRecomment(long bno);

    int getadreComCount(long bno);
}
