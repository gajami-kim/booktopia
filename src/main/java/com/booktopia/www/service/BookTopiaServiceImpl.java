package com.booktopia.www.service;

import com.booktopia.www.domain.BookVO;
import com.booktopia.www.domain.BooktopiaVO;
import com.booktopia.www.repository.BookMapper;
import com.booktopia.www.repository.BookTopiaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookTopiaServiceImpl implements BookTopiaService{

    private final BookTopiaMapper bookTopiaMapper;
    private final BookMapper bookMapper;

    @Override
    public List<BookVO> getList(String btnResult) {
        BookVO bookVO = new BookVO();
        List<BookVO> list = null;
                switch (btnResult){
            case "10": case "11" :
                bookVO.setResultType("A");
                list = bookTopiaMapper.getListType(bookVO.getResultType());
                break;
            case "12": case "13" :
                bookVO.setResultType("B");
                list = bookTopiaMapper.getListType(bookVO.getResultType());
                break;
            case "14": case "15" :
                bookVO.setResultType("C");
                list = bookTopiaMapper.getListType(bookVO.getResultType());
                break;
            case "16": case "17" :
                bookVO.setResultType("D");
                list = bookTopiaMapper.getListType(bookVO.getResultType());
                break;
            case "18": case "19" :
                bookVO.setResultType("E");
                list = bookTopiaMapper.getListType(bookVO.getResultType());
                break;
            case "20" :
                bookVO.setResultType("F");
                list = bookTopiaMapper.getListType(bookVO.getResultType());
                break;
        }
        return list;
    }

    @Override
    public void insert(BooktopiaVO booktopiaVO) {
        bookTopiaMapper.insert(booktopiaVO);
    }

    @Override
    public List<BookVO> getBookList(int book) {
        return bookMapper.getBookList(book);
    }

    @Override
    public List<BookVO> findType(String user) {
        String resultType = bookTopiaMapper.findType(user);
        BookVO bookVO = new BookVO();
        List<BookVO> list = null;
        if(resultType == null){
            list = null;
            return list;
        }else {
            switch (resultType) {
                case "10":
                case "11":
                    bookVO.setResultType("A");
                    list = bookTopiaMapper.getListType(bookVO.getResultType());
                    break;
                case "12":
                case "13":
                    bookVO.setResultType("B");
                    list = bookTopiaMapper.getListType(bookVO.getResultType());
                    break;
                case "14":
                case "15":
                    bookVO.setResultType("C");
                    list = bookTopiaMapper.getListType(bookVO.getResultType());
                    break;
                case "16":
                case "17":
                    bookVO.setResultType("D");
                    list = bookTopiaMapper.getListType(bookVO.getResultType());
                    break;
                case "18":
                case "19":
                    bookVO.setResultType("E");
                    list = bookTopiaMapper.getListType(bookVO.getResultType());
                    break;
                case "20":
                    bookVO.setResultType("F");
                    list = bookTopiaMapper.getListType(bookVO.getResultType());
                    break;

            }
            return list;
        }

    }

}
