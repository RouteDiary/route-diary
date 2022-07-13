package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Diary;
import com.my.exception.SelectException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

/**
 * Servlet implementation class DiaryboardServlet
 */
@WebServlet("/diaryboard")
public class DiaryboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();

    int viewStatus = Integer.parseInt(request.getParameter("view_status"));
    // 1 - 최신순 / 2 - 조회순 / 3 - 좋아요순
    int currentPage = 1;
    String strCurrentPage = request.getParameter("current_page");
    if (strCurrentPage != null && !strCurrentPage.equals("")) {
      currentPage = Integer.parseInt(strCurrentPage);
    }

    int countPerPage = 10; // 한페이지당 보여줄 목록수
    int endRow = currentPage * countPerPage; // 한페이지에 보여줄 마지막 행번호
    int startRow = endRow - countPerPage + 1; // 한페이지에 보여줄 첫 행번호

    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    int totalRows = 0;
    try {
      int diaryDisClosureFlag = 1;
      totalRows = diaryRepository.selectDiariesRowSize(diaryDisClosureFlag);
      System.out.println("현재 페이지 : " + currentPage + " / " + startRow + " : " + endRow
          + " / 전체다이어리 갯수 : " + totalRows);
      // 전체 다이어리 : 96개 / endRow : 100 -> 아지막 argument를 totalRows로 함
      // 전체 다이어리 : 197개 / endRow : 190 -> 마지막 argument를 endRow로 함
      List<Diary> diaries = null;

      switch (viewStatus) {
        case 1: // 최신순
          if (totalRows < endRow) {
            diaries = diaryRepository.selectDiariesByWritingDate(startRow, totalRows);
          } else {
            diaries = diaryRepository.selectDiariesByWritingDate(startRow, endRow);
          }
          map.put("status", 1);
          map.put("message", "최신순으로 가져");
          break;
        case 2: // 조회수
          if (totalRows < endRow) {
            diaries = diaryRepository.selectDiariesByViewCnt(startRow, totalRows);
          } else {
            diaries = diaryRepository.selectDiariesByViewCnt(startRow, endRow);
          }
          map.put("status", 2);
          break;
        case 3: // 좋아요
          if (totalRows < endRow) {
            diaries = diaryRepository.selectDiariesByLikeCnt(startRow, totalRows);
          } else {
            diaries = diaryRepository.selectDiariesByLikeCnt(startRow, endRow);
          }
          map.put("status", 3);
          break;
        default:
          map.put("status", 0);
          map.put("message", "가져온 다이어리가 없습니다.");
      }
      if (diaries.size() == 0 || diaries == null) {
        map.put("status", 0);
        map.put("message", "가져온 다이어리가 없습니다.");
      } else {
        map.put("diaries", diaries);
      }

    } catch (SelectException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }

}
