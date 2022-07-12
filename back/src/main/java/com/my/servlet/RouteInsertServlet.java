package com.my.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

/**
 * Servlet implementation class RouteInsertServlet
 */
@WebServlet("/routeinsert")
public class RouteInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    int routeNo = Integer.parseInt(request.getParameter("route_no"));
    int sightNo = Integer.parseInt(request.getParameter("sight_no"));
    String routeContent = request.getParameter("route_content");
  }


}


/*
 * INSERT INTO ROUTES VALUES (1 , (SELECT NVL(MAX(route_no), 0) + 1 FROM routes WHERE diary_no = 1)
 * , 1 , '루트내용');
 */
