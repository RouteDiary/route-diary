package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

    // 테스트용 코드
    try {
      List<Diary> diaries = diaryRepository.selectDirariesByWritingDate(11, 20);
      List<Diary> diaries2 = diaryRepository.selectDirariesByViewCnt(11, 20);
      List<Diary> diaries3 = diaryRepository.selectDirariesByLikeCnt(11, 20);
      List<Diary> diaries4 = diaryRepository.selectDirariesById("a", 11, 20);
      List<Diary> diaries5 = diaryRepository.selectDirariesByKeyword("경복궁", 11, 20);
      for (int i = 0; i < diaries.size(); i++) {
        out.print(i + 1 + " : " + diaries.get(i).toString() + "<br>");
      }
      out.print("<br> -------------<br>");
      for (int i = 0; i < diaries2.size(); i++) {
        out.print(i + 1 + " : " + diaries2.get(i).toString() + "<br>");
      }
      out.print("<br> -------------<br>");
      for (int i = 0; i < diaries3.size(); i++) {
        out.print(i + 1 + " : " + diaries3.get(i).toString() + "<br>");
      }
      out.print("<br> -------------<br>");
      for (int i = 0; i < diaries4.size(); i++) {
        out.print(i + 1 + " : " + diaries4.get(i).toString() + "<br>");
      }
      out.print("<br> -------------<br>");
      for (int i = 0; i < diaries5.size(); i++) {
        out.print(i + 1 + " : " + diaries5.get(i).toString() + "<br>");
      }
    } catch (SelectException e) {
      e.printStackTrace();
    }

  }

}
