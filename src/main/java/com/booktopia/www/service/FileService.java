package com.booktopia.www.service;

import com.booktopia.www.domain.FileVO;
import com.booktopia.www.domain.PagingVO;

import java.util.List;

public interface FileService {
    List<FileVO> getFileList();

    void insertFile(long bno);
}
