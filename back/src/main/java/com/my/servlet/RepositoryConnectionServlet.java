package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Like;
import com.my.exception.InsertException;
import com.my.repository.LikeOracleRepository;
import com.my.repository.LikeRepository;

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
    LikeRepository likeRepository = new LikeOracleRepository(envPath);
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    Like like = new Like(1, "a");
    // 테스트용 코드
    try {
      likeRepository.insertLike(like);

    } catch (InsertException e) {
      e.printStackTrace();
    }

  }

}
