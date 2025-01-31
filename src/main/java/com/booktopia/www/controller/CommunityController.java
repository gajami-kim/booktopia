package com.booktopia.www.controller;

import com.booktopia.www.domain.BoardVO;
import com.booktopia.www.domain.PagingVO;
import com.booktopia.www.domain.VoteVO;
import com.booktopia.www.handler.PagingHandler;
import com.booktopia.www.repository.SystemInfoMapper;
import com.booktopia.www.service.BoardService;
import com.booktopia.www.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/community/*")
@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final BoardService bsv;
    private final VoteService voteService;
    private final SystemInfoMapper systemInfoMapper;

    @GetMapping("/communityMain")
    public String commMain(Model model){
        int Score = systemInfoMapper.getScore();

        model.addAttribute("score",Score);
        return "/community/communityMain";
    }

    // 커뮤니티 찬반투표
    @PostMapping("/push")
    @ResponseBody
    public String votePush (@RequestBody VoteVO voteVO){

        String id = voteVO.getId(); // 들어온 voteVO에서 id만 추출
        String result = voteVO.getVoteResult();
        VoteVO vo = voteService.getUser(id); // DB에서 id 만 추출

        if(vo == null) {
            voteService.insert(voteVO);
            return "1";
        }
        //셀렉트 id 때서 객체로 받아와서 select *
        // null 아니면 참여 가능
        return "0";
    }

    // id 값이 있는지 체크
    // 있을 경우에는 "1"을 리턴 / 아닐경우에는 "0"을 리턴
    @GetMapping(value = "/{user}")
    @ResponseBody
    public String hasVote (@PathVariable("user") String id){

        VoteVO vo = voteService.getUser(id); // DB에서 id 만 추출

        if(vo == null) {
            return "0";
        }
        return "1";
    }

    @GetMapping("/communityListAll")
    public String commListAll(Model m, PagingVO pgvo){

        //전체 게시글 수
        int totalCount = bsv.getTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo,totalCount);
        List<BoardVO> blist = bsv.getList(pgvo);
        int AllcateCtn = bsv.getCateCount();

        m.addAttribute("blist",blist);
        m.addAttribute("ph",ph);
        m.addAttribute("AllcateCtn",AllcateCtn);
        return "/community/communityList";
    }


    @GetMapping("/communityList")
    public void commListCategory(Model m, PagingVO pgvo, @RequestParam("bCate")String bCate){

        //전체 게시글 수
        int totalCount = bsv.getCateTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo,totalCount);
        List<BoardVO> blist = bsv.getCateList(pgvo);
        int cateCtn = bsv.getCategoryCount(bCate);

        m.addAttribute("cateCtn",cateCtn);
        m.addAttribute("blist",blist);
        m.addAttribute("ph",ph);
    }


    @GetMapping("/communityNotice")
    public void commNotice(){}



}
