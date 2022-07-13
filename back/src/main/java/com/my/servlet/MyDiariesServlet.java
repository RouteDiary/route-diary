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

@WebServlet("/mydiaries")
public class MyDiariesServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");

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
    try {
      int totalRows =
          diaryRepository.selectDiariesRowSize(1) + diaryRepository.selectDiariesRowSize(0);
      System.out.println("현재 페이지 : " + currentPage + " / " + startRow + " : " + endRow
          + " / 전체다이어리 갯수 : " + totalRows);
      // 전체 다이어리 : 96개 / endRow : 100 -> 아지막 argument를 totalRows로 함
      // 전체 다이어리 : 197개 / endRow : 190 -> 마지막 argument를 endRow로 함
      List<Diary> diaries = null;
      if (totalRows < endRow) {
        diaries = diaryRepository.selectDiariesById(clientId, startRow, totalRows);
      } else {
        diaries = diaryRepository.selectDiariesById(clientId, startRow, endRow);
      }
      if (diaries.size() == 0 || diaries == null) {
        map.put("status", 0);
        map.put("message", "가져온 다이어리가 없습니다.");
      } else {
        map.put("status", 1);
        map.put("message", diaries.size() + "개의 다이어리를 가져왔습니다.");
        map.put("diaries", diaries);
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
