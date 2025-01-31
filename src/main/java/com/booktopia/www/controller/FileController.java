package com.booktopia.www.controller;

import com.booktopia.www.domain.FileVO;
import com.booktopia.www.repository.FileMapper;
import com.booktopia.www.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/file/*")
public class FileController {
    private final FileMapper fileMapper;

    //저장경로(윈도우)
    private final String UP_DIR = "C:\\_booktopia\\_fileUpload\\";

    //이미지업로드용
    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("image") final MultipartFile image, Model m) {
        if(image == null || image.isEmpty()) {
            return "";
        }

        String originalFilename = image.getOriginalFilename();
        String onlyFilename = originalFilename.substring(originalFilename.lastIndexOf(File.separator)+1);

        String uuid = UUID.randomUUID().toString();
        String extention = onlyFilename.substring(onlyFilename.lastIndexOf("_")+1);
        String saveFilename = uuid+"_"+extention;
        String fileFullPath = Paths.get(UP_DIR,saveFilename).toString();

        FileVO fvo = new FileVO();
        fvo.setUuid(uuid);
        fvo.setSaveDir("/board");
        fvo.setFileSize(image.getSize());
        fvo.setFileName(saveFilename);
        fvo.setFileType(1);


        fileMapper.insertFile(fvo);

        File dir = new File(UP_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try{
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            m.addAttribute("fvo",fvo);
            return saveFilename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping(value = "/filePrint", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] printEditorImage(@RequestParam("fileName") final String fileName) {
        String fileFullPath = Paths.get(UP_DIR, fileName).toString();

        //파일이 없는 경우
        File upLoadFile = new File(fileFullPath);
        if(!upLoadFile.exists()) {
            throw new RuntimeException();
        }

        try {
            //이미지 파일 byte[]로 변환 후 반환
            return Files.readAllBytes(upLoadFile.toPath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
