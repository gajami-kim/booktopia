package com.booktopia.www.repository;

import com.booktopia.www.domain.BookVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<BookVO> getBookList(int book);
}
