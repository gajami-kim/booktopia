package com.booktopia.www.repository;

import com.booktopia.www.domain.BookVO;
import com.booktopia.www.domain.BooktopiaVO;
import com.booktopia.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookTopiaMapper {

    List<BookVO> getList(String btnResult);

    List<BookVO> getListType(String ResultType);

    void insert(BooktopiaVO booktopiaVO);

    List<BooktopiaVO> bTestList();

    int getTotal();

    List<BooktopiaVO> adminTestList(PagingVO pgvo);

    String findType(String user);
}
