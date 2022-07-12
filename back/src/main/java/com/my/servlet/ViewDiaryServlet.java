package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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

@WebServlet("/viewdiary")
public class ViewDiaryServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Diary diary = new Diary();
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));

    Map<String, Object> map = new HashMap<String, Object>();

    try {
      diary = diaryRepository.selectDiaryByDiaryNo(diaryNo);
      String result = mapper.writeValueAsString(map);
      out.print(result);
    } catch (SelectException e) {
      e.printStackTrace();
    }
    out.print(diary);
  }
}
