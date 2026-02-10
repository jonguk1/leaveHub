package com.example.leaveHub.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum; // 현재 페이지 번호
    private int amount; // 페이지당 게시물 수

    public Criteria() {
        this(1, 10); // 기본값: 1페이지, 페이지당 10개 게시물
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public int getOffset() {
        return (this.pageNum - 1) * this.amount;
    }

}
