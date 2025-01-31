package com.booktopia.www.domain.DTO;

import com.booktopia.www.domain.CommentVO;
import com.booktopia.www.domain.RecommentVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CommentDTO {
    private CommentVO cvo;
    private List<RecommentVO> rclist;
}
