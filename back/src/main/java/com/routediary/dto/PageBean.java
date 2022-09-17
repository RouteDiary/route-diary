package com.routediary.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageBean<T> {
  private List<T> posts; // 다이어리 or 공지사항의 list
  private int currentPage; // 현재 페이지넘버
  private int totalPage; // 전체 페이지 수
  private int startPage; // (현재 페이지를 기준으로) 시작페이지 ex. 현재 페이지가 25인 경우, startPage = 21
  private int endPage; // (현재 페이지를 기준으로) 마지막페이지 ex. 현재 페이지가 25인 경우, endPage = 30
  private int cntPerPageGroup; // 한 화면에 보여줄 페이지 갯수
  private int cntPerPage; // 한 페이지에 보여줄 다이어리 or 공지사항의 갯수

  /**
   * 파라미터에 대한 설명
   * 
   * posts : 다이어리 or 공지사항의 list / totalRows : DB에 저장되있는 다이어리 or 공지사항의 총 row 수 / currentPage : 현재 페이지
   * 번호 / cntPerPageGroup : 한 화면에 보여줄 페이지 갯수 / cntPerPage : 한 페이지에 보여줄 다이어리 or 공지사항의 갯수
   * 
   * @param posts
   * @param totalRows
   * @param currentPage
   * @param cntPerPageGroup
   * @param cntPerPage
   */
  public PageBean(List<T> posts, long totalRows, int currentPage, int cntPerPageGroup,
      int cntPerPage) {
    this.posts = posts;
    this.currentPage = currentPage;
    this.cntPerPageGroup = cntPerPageGroup;
    this.cntPerPage = cntPerPage;

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
