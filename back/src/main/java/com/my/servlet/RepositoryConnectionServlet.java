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

/**
 * Servlet implementation class RepositoryConnectionServlet
 */
@WebServlet("/repositoryconnection")
public class RepositoryConnectionServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public RepositoryConnectionServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    Diary diary = new Diary();
    try {
      diary = diaryRepository.selectDiraryByDiaryNo(45);
    } catch (SelectException e) {
      e.printStackTrace();
    }
    out.print(diary);


  }

}
