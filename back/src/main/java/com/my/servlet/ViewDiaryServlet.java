package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Diary;
import com.my.exception.SelectException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

@WebServlet("/viewdiary")
public class ViewDiaryServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();
    Diary diary = new Diary();
    try {
      diary = diaryRepository.selectDiraryByDiaryNo(diary.getDiaryNo());
    } catch (SelectException e) {
      e.printStackTrace();
    }
    out.print(diary);
  }
}
