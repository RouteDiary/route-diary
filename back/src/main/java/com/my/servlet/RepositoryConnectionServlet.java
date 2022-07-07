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
      // List<Diary> diaries = diaryRepository.selectDirariesFromFirstRowByWritingDate(10);
      // List<Diary> diaries2 = diaryRepository.selectDirariesFromMiddleRowByWritingDate(11, 20);
      // List<Diary> diaries = diaryRepository.selectDirariesFromFirstRowByViewCnt(10);
      // List<Diary> diaries2 = diaryRepository.selectDirariesFromMiddleRowByViewCnt(11, 20);
      // List<Diary> diaries = diaryRepository.selectDirariesFromFirstRowByLikeCnt(10);
      // List<Diary> diaries2 = diaryRepository.selectDirariesFromMiddleRowByLikeCnt(11, 20);
      // List<Diary> diaries = diaryRepository.selectDirariesFromFirstRowById("a", 10);
      // List<Diary> diaries2 = diaryRepository.selectDirariesFromMiddleRowById("a", 11, 20);
      List<Diary> diaries = diaryRepository.selectDirariesFromFirstRowByKeyword("경복궁", 10);
      List<Diary> diaries2 = diaryRepository.selectDirariesFromMiddleRowByKeyword("경복궁", 11, 20);
      for (int i = 0; i < diaries.size(); i++) {
        out.print(i + 1 + " : " + diaries.get(i).toString() + "<br>");
      }
      out.print("<br><br>--------<br>");
      for (int i = 0; i < diaries2.size(); i++) {
        out.print(i + 1 + " : " + diaries2.get(i).toString() + "<br>");
      }
    } catch (SelectException e) {
      e.printStackTrace();
    }

  }

}
