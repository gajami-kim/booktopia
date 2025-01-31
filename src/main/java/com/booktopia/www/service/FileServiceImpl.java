package com.booktopia.www.service;

import com.booktopia.www.domain.FileVO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{
    private final FileMapper fileMapper;

    @Override
    public List<FileVO> getFileList() {
        return fileMapper.getFileList();
    }

    @Override
    public void insertFile(long bno) {
        fileMapper.insertFile2(bno);
    }
}
