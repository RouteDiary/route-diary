package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Diary;
import com.my.dto.Route;
import com.my.dto.Sight;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;
import com.my.repository.RouteOracleRepository;
import com.my.repository.RouteRepository;
import com.my.repository.SightOracleRepository;
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
    RouteRepository routeRepository = new RouteOracleRepository(envPath);
    SightRepository sightRepository = new SightOracleRepository(envPath);
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Sight sight = new Sight(23, null, null, 20001, null);
    Route route = new Route(46, 19, "새루트", sight);
    try {
      Diary diary = diaryRepository.selectDiraryByDiaryNo(46);
      routeRepository.insert(route);
      diaryRepository.insert(diary);
    } catch (SelectException e) {
      e.printStackTrace();
    } catch (InsertException e) {
      e.printStackTrace();
    }
    out.print(route);

  }

}
