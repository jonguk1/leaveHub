package com.example.leaveHub.dto;

import com.example.leaveHub.common.Criteria;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

    private int startPage;
    private int endPage;
    private boolean prev, next;
    private int total;
    private int realEnd;
    private Criteria cri;

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        // 끝 페이지 계산
        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
        // 시작 페이지 계산
        this.startPage = this.endPage - 9;

        // 실제 끝 페이지 계산
        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

        // 데이터가 아예 없는 경우 realEnd를 1로 설정하여 1페이지는 보이게 함
        if (this.realEnd <= 0) {
            this.realEnd = 1;
        }

        // 끝 페이지 조정
        if (this.realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        // 이전, 다음 버튼 여부 결정
        this.prev = this.startPage > 1;
        this.next = this.endPage < this.realEnd;
    }

}
