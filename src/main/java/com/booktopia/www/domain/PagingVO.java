package com.booktopia.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingVO {

    private int pageNo;
    private int qty;
    private String type;
    private String keyword;
    private String bCate;

    public PagingVO(){
        this.pageNo=1;
        this.qty=10;
    }

    public PagingVO(int pageNo, int qty){
        this.pageNo=pageNo;
        this.qty=qty;
    }

    public PagingVO(int pageNo, int qty, String type, String keyword, String bCate){
        this.pageNo=pageNo;
        this.qty=qty;
        this.type=type;
        this.keyword=keyword;
        this.bCate=bCate;
    }

    public int getPageStart(){
        return (pageNo-1)*qty;
    }

    public String[] getTypeToArray(){
        return this.type==null? new String[]{}:this.type.split("");
    }

}
