package com.routediary.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {
  private List<T> boards; // 다이어리 or 공지사항의 list
  private int currentPage; // 현재 페이지넘버
  private int totalPage; // 전체 페이지 수
  private int startPage; // (현재 페이지를 기준으로) 시작페이지 ex. 현재 페이지가 25인 경우, startPage = 21
  private int endPage; // (현재 페이지를 기준으로) 마지막페이지 ex. 현재 페이지가 25인 경우, endPage = 30
  private int cntPerPageGroup; // 한 화면에 보여줄 페이지 수


  /**
   * 
   * @param boards 페이지의 목록
   * @param totalRows DB에서 select할 수 있는 총 행수
   * @param currentPage 검색할페이지
   * @param cntPerPageGroup //페이지그룹별 보여줄 페이지수
   * @param cntPerPage //한페이지당 보여줄 목록수
   */
  public PageBean(List<T> boards, long totalRows, int currentPage, int cntPerPageGroup,
      int cntPerPage) {
    this.boards = boards;
    this.currentPage = currentPage;
    this.cntPerPageGroup = cntPerPageGroup;

    this.totalPage = (int) Math.ceil((double) totalRows / cntPerPage);

    if (this.currentPage > this.totalPage) {
      this.currentPage = this.totalPage;
    }

    this.endPage = (int) (Math.ceil((double) currentPage / cntPerPageGroup) * cntPerPageGroup);
    this.startPage = this.endPage - cntPerPageGroup + 1;
    if (this.totalPage < this.endPage) {
      this.endPage = this.totalPage;
    }
  }
}
