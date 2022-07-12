package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<>();
    HttpSession session = request.getSession();
    String clientId = request.getParameter("client_id");

    int currentPage = 1;
    String strCurrentPage = request.getParameter("currentPage");
    if (strCurrentPage != null && !strCurrentPage.equals("")) {
      currentPage = Integer.parseInt(strCurrentPage);
    }

    List<Diary> diary = new ArrayList<Diary>();
    int cntPerPage = 10; // 한페이지당 보여줄 목록수
    int endRow = currentPage * cntPerPage; // 한페이지에 보여줄 마지막 행번호
    int startRow = endRow - cntPerPage + 1; // 한페이지에 보여줄 첫 행번호
    System.out.println(startRow + ":" + endRow);
    try {
      List<Diary> list = diaryRepository.selectDiariesById(clientId, startRow, endRow);
      System.out.println("페이지의 다이어리개수" + list.size());
      System.out.println(list);
      map.put("status", 1);
      map.put("diaries", list);
    } catch (SelectException e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("msg", e.getMessage());
    }
    out.print(mapper.writeValueAsString(map));
  }

}
