package com.booktopia.www.handler;

import com.booktopia.www.domain.*;
import com.booktopia.www.domain.DTO.CommentDTO;
import com.booktopia.www.domain.DTO.OrderInfoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class PagingHandler {
    private int startPage;
    private int endPage;
    private int realEndPage;
    private boolean prev,next;
    private int totalCount;
    private PagingVO pgvo;

    private List<BoardVO> boardlist;
    private List<DeliveryVO> deliveries;
    private List<OrderInfoDTO> orderInfoDTOList;
    private List<BooktopiaVO> booktopia;
    private List<UserVO> userList;
    private List<AdCouponVO> adCouponList;
    private List<CommentDTO> cmtList;
    private List<QnaVO> qnaList;

    public PagingHandler(PagingVO pgvo, int totalCount) {
        this.pgvo=pgvo;
        this.totalCount = totalCount;

        this.endPage= (int) (Math.ceil(pgvo.getPageNo()/(double)10)*10);
        this.startPage = endPage-9;

        this.realEndPage = (int) Math.ceil(totalCount/(double)pgvo.getQty());

        if(realEndPage<endPage) {
            this.endPage=realEndPage;
        }

        this.prev=this.startPage>1;
        this.next=this.endPage<realEndPage;
    }


    public PagingHandler(PagingVO pgvo, int totalCount, List<CommentDTO> cmtList){
        this(pgvo,totalCount);
        this.cmtList=cmtList;
    }
}
