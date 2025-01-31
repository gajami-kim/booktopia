package com.booktopia.www.controller;

import com.booktopia.www.domain.BookVO;
import com.booktopia.www.domain.BooktopiaVO;
import com.booktopia.www.service.BookTopiaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/booktopiaTest/*")
@RequiredArgsConstructor
@Slf4j
public class BooktopiaTestController {
    private final BookTopiaService bookTopiaService;

    @GetMapping("/test")
    public void getBooktopia(){}

    @GetMapping("/result")
    public void getResult(){}


    @GetMapping("/resultTest/{user}")
    @ResponseBody
    public List<BookVO> showResult(@PathVariable("user") String user, Model m){
        List<BookVO> list = bookTopiaService.findType(user);
        return list;
    }
    @PostMapping("/bookList")
    public String btnResult(@RequestParam("btnResult") String btnResult, Model m, BooktopiaVO booktopiaVO) {

        int randomNumber1;
        do {
            randomNumber1 = generateRandomNumber(10, 20);
        } while (randomNumber1 == Integer.parseInt(btnResult));

        int randomNumber2;
        do {
            randomNumber2 = generateRandomNumber(10, 20);
        } while (randomNumber2 == Integer.parseInt(btnResult) || randomNumber2 == randomNumber1);

        List<BookVO> blist = bookTopiaService.getList(btnResult);
        List<BookVO> popblist = bookTopiaService.getList(String.valueOf(randomNumber1));
        List<BookVO> newblist = bookTopiaService.getList(String.valueOf(randomNumber2));

        bookTopiaService.insert(booktopiaVO);
        m.addAttribute("blist", blist);
        m.addAttribute("popblist", popblist);
        m.addAttribute("newblist", newblist);

        return "/booktopiaTest/bookList";
    }

    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    @GetMapping("/detail")
    public void bookDetail(@RequestParam("book") int book, Model m, BookVO bookVO) {
        List<BookVO> blist = bookTopiaService.getBookList(book);
        m.addAttribute("blist", blist);
    }

}



