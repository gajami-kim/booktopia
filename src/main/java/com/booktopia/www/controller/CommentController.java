package com.booktopia.www.controller;

import com.booktopia.www.domain.CommentVO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.RecommentVO;
import com.booktopia.www.handler.PagingHandler;
import com.booktopia.www.service.BoardService;
import com.booktopia.www.service.CommentService;
import com.booktopia.www.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class CommentController {

    private final CommentService comsv;
    private final ReCommentService rcomsv;
    private final BoardService bsv;

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<String> postComment(@RequestBody JSONObject cvo) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(String.valueOf(cvo));

        CommentVO commentVO = new CommentVO();
        commentVO.setBno(Long.parseLong((String) obj.get("bno")));
        commentVO.setCWriter((String) obj.get("cWriter"));
        commentVO.setCContent((String) obj.get("cContent"));

        int isOk = comsv.post(commentVO);
        bsv.updateCommentCnt(commentVO.getBno());
        return isOk>0 ? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{bno}/{page}")
    @ResponseBody
    public ResponseEntity<PagingHandler> getComment(@PathVariable("bno") int bno,@PathVariable("page") int page){
        PagingVO pgvo = new PagingVO(page,5);
        PagingHandler ph = comsv.getCommentList(bno,pgvo);

        return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
    }

    @PutMapping("/modify")
    @ResponseBody
    public String modifyComment(@RequestBody JSONObject cvo) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(String.valueOf(cvo));

        CommentVO commentVO = new CommentVO();
        commentVO.setCno(Long.parseLong((String)obj.get("cno")));
        commentVO.setCContent((String) obj.get("cContent"));

        int isOk = comsv.modify(commentVO);
        return isOk>0 ? "1":"0";
    }

    @DeleteMapping("/{cno}")
    @ResponseBody
    public String deleteComment(@PathVariable("cno")long cno) {
        // 댓글 수 삭제 먼저
        Long bvo = comsv.getCommentBno(cno);
        bsv.deleteCommentCnt(bvo, cno);

        //댓글 삭제
        int isOk = comsv.deleteComment(cno);
        return isOk>0 ? "1":"0";
    }

    @PostMapping("/post/comment")
    @ResponseBody
    public ResponseEntity<String> postReComment(@RequestBody JSONObject rcvo) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(String.valueOf(rcvo));

        RecommentVO rvo = new RecommentVO();
        rvo.setCno(Long.parseLong((String)obj.get("cno")));
        rvo.setBno(Integer.parseInt((String)obj.get("bno")));
        rvo.setRcWriter((String) obj.get("rcWriter"));
        rvo.setRcContent((String) obj.get("rcContent"));

        int isOk = rcomsv.postromment(rvo);
        bsv.updateCommentCnt(rvo.getBno());
        return isOk>0 ? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
