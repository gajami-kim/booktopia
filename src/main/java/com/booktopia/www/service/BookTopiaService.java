package com.booktopia.www.service;

import com.booktopia.www.domain.BookVO;
import com.booktopia.www.domain.BooktopiaVO;

import java.util.List;

public interface BookTopiaService {

    List<BookVO> getList(String btnResult);

    void insert(BooktopiaVO booktopiaVO);

    List<BookVO> getBookList(int book);

    List<BookVO> findType(String user);
}
