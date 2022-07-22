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
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Diary;
import com.my.exception.SelectException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

@WebServlet("/diaryboard")
public class DiaryboardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();

    HttpSession session = request.getSession();
    System.out.println(session);

    // view_status : 1 - 최신순 / 2 - 조회순 / 3 - 좋아요순
    int viewStatus = Integer.parseInt(request.getParameter("view_status"));
    String keyword = request.getParameter("keyword");
    int currentPage = 1;
    int strartPage = ((currentPage - 1) * 10) * 10 + 1;
    String strCurrentPage = request.getParameter("current_page");
    if (strCurrentPage != null && !strCurrentPage.equals("")) {
      currentPage = Integer.parseInt(strCurrentPage);
    }



    String envPath = getServletContext().getRealPath("project.properties");
    Diary diary = new Diary();
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    int totalRows = 0;
    try {
      int diaryDisClosureFlag = 1;
      totalRows = diaryRepository.selectDiariesRowSize(diaryDisClosureFlag);

      if ((currentPage + 1) * 10 > totalRows && totalRows != 0) {
        currentPage = (totalRows / 10) + 1;
      }
      int countPerPage = 10; // 한페이지당 보여줄 목록수
      int endRow = currentPage * countPerPage; // 한페이지에 보여줄 마지막 행번호
      int startRow = endRow - countPerPage + 1; // 한페이지에 보여줄 첫 행번호
      // 전체 다이어리 : 96개 / endRow : 100 -> 아지막 argument를 totalRows로 함
      // 전체 다이어리 : 197개 / endRow : 190 -> 마지막 argument를 endRow로 함
      System.out.println("현재 페이지 : " + currentPage + " / 보여줄 diary row -  " + startRow + " : "
          + endRow + " / 전체다이어리 갯수 : " + totalRows);
      List<Diary> diaries = null;

      System.out.println("viewStatus=" + viewStatus);
      if (viewStatus == 1) { // 최신순
        if (totalRows < endRow) {
          diaries = diaryRepository.selectDiariesByKeywordOrderedByColumnNameInDiariesTable(keyword,
              "diary_writing_time", startRow, totalRows);
        } else {
          diaries = diaryRepository.selectDiariesByKeywordOrderedByColumnNameInDiariesTable(keyword,
              "diary_writing_time", startRow, endRow);
        }
        map.put("status", 1);
        map.put("message", "최신순으로 다이어리를 가져옴 (" + diaries.size() + "개)");
      } else if (viewStatus == 2) { // 조회수순
        if (totalRows < endRow) {
          diaries = diaryRepository.selectDiariesByKeywordOrderedByColumnNameInDiariesTable(keyword,
              "diary_view_cnt", startRow, totalRows);
        } else {
          diaries = diaryRepository.selectDiariesByKeywordOrderedByColumnNameInDiariesTable(keyword,
              "diary_view_cnt", startRow, endRow);
        }
        map.put("status", 2);
        map.put("message", "조회수순으로 다이어리를 가져옴 (" + diaries.size() + "개)");
      } else if (viewStatus == 3) { // 좋아요순
        if (totalRows < endRow) {

          diaries = diaryRepository.selectDiariesByKeywordOrderedByColumnNameInDiariesTable(keyword,
              "diary_like_cnt", startRow, totalRows);
        } else {
          diaries = diaryRepository.selectDiariesByKeywordOrderedByColumnNameInDiariesTable(keyword,
              "diary_like_cnt", startRow, endRow);
        }
        map.put("status", 3);
        map.put("message", "좋아요수순으로 다이어리를 가져옴 (" + diaries.size() + "개)");
      } else {
        map.put("status", 0);
        map.put("message", "가져온 다이어리가 없습니다.");
      }

      if (diaries.size() == 0 || diaries == null) {
        System.out.println("in size");
        map.put("status", 0);
        map.put("message", "가져온 다이어리가 없습니다.");
      } else {
        map.put("diaries", diaries);
        map.put("totalRows", totalRows);
        map.put("currentPage", currentPage);
        map.put(envPath, diaries);
      }
    } catch (SelectException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
