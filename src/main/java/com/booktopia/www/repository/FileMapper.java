package com.booktopia.www.repository;

import com.booktopia.www.domain.FileVO;
import com.booktopia.www.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertFile(FileVO fvo);

    List<FileVO> getFileList();

    void insertFile2(long bno);
}
