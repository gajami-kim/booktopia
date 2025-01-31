package com.booktopia.www.controller;

import com.booktopia.www.domain.*;
import com.booktopia.www.handler.PagingHandler;
import com.booktopia.www.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
    private final BoardService bsv;
    private final ReCommentService rcsv;
    private final HeartService hsv;

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody JSONObject bvo) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(String.valueOf(bvo));

        BoardVO boardVO = new BoardVO();
        boardVO.setId((String) obj.get("id"));
        boardVO.setBTitle((String) obj.get("bTitle"));
        boardVO.setBContent((String) obj.get("bContent"));
        boardVO.setBWriter((String) obj.get("bWriter"));
        boardVO.setBCate((String) obj.get("bCate"));
        boardVO.setBMainImg((String) obj.get("bMainImg"));


        int isOk = bsv.insert(boardVO);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/communityList")
    public void commList(Model m, PagingVO pgvo) {
        //전체 게시글 수
        int totalCount = bsv.getTotalCount(pgvo);

        PagingHandler ph = new PagingHandler(pgvo, totalCount);
    }

    @GetMapping("/detail")
    public void detailWithId(Model m, @RequestParam("bno") long bno, @RequestParam("id") String id){
        BoardVO bvo = bsv.getDetail(bno);
        int heartCnt = bsv.getHeartCnt(bno);
        Integer hvo = hsv.getHeartYN(bno,id);
        if(hvo==null) {
            hvo=0;
        }
        m.addAttribute("hvo",hvo);
        m.addAttribute("heartCnt",heartCnt);
        m.addAttribute("bvo", bvo);

    }

    @GetMapping("/modify")
    public void detail(Model m, @RequestParam("bno") long bno) {
        BoardVO bvo = bsv.getDetail(bno);
        m.addAttribute("bvo", bvo);
    }

    @PostMapping("/modify")
    public String modify(@RequestBody JSONObject bvo) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(String.valueOf(bvo));

        BoardVO boardVO = new BoardVO();
        boardVO.setBno(Long.parseLong((String) obj.get("bno")));
        boardVO.setId((String) obj.get("id"));
        boardVO.setBTitle((String) obj.get("bTitle"));
        boardVO.setBContent((String) obj.get("bContent"));
        boardVO.setBWriter((String) obj.get("bWriter"));
        boardVO.setBCate((String) obj.get("bCate"));
        boardVO.setBMainImg((String) obj.get("bMainImg"));

        bsv.modify(boardVO);
        return "/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno") long bno) {
        bsv.delete(bno);
        rcsv.deleteCommentFromBoard(bno);
        return "redirect:/community/communityListAll";
    }

    @PostMapping("/heart/{bno}")
    @ResponseBody
    public String heart(@RequestBody HeartVO hvo, @PathVariable("bno") long bno) {

        String id = hvo.getId();

        HeartVO heartVO = hsv.heart1User(id,hvo.getBno());
        if (heartVO != null) {
            hsv.updateHeart(id,hvo.getBno());
            bsv.updateHeartCount(bno);
            return "0";
        } else {
            hsv.insertHeart(hvo);
            bsv.updateHeartCount(bno);
            return "1";
        }
    }

    @GetMapping("/heart/{bno}/{id}")
    @ResponseBody
    public String getHeartBno(@PathVariable("bno") long bno, @PathVariable("id") String id) {
        HeartVO heartVO = hsv.getUser(id, bno);
        if (heartVO == null) {
            return "0";
        } return "1";

    }

    @DeleteMapping("/heart/delete/{bno}/{id}")
    @ResponseBody
    public String deleteHeart(@PathVariable("bno") long bno, @PathVariable("id")String id){
        bsv.deleteHeartCnt(bno);

        int isOk = hsv.deleteHeart(bno, id);
        return isOk>0?"1":"0";
    }


}