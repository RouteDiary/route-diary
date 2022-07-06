package com.my.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.repository.AdminRepository;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;
import com.my.repository.CommentRepository;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;
import com.my.repository.LikeRepository;
import com.my.repository.RouteRepository;
import com.my.repository.SightRepository;

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
    AdminRepository adminRepository = new AdminOracleRepository(envPath);
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    LikeRepository likeRepository = new LikeOracleRepository(envPath);
    RouteRepository routeRepository = new RouteOracleRepository(envPath);
    SightRepository sightRepository = new SightOracleRepository(envPath);


    // 테스트용 코드
    diaryRepository.selectDirariesByWritingDate();

  }

}
