package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 안쓰는 서블릿임


@WebServlet("/sightinfo")
public class SightInfoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();
    int sightNo = Integer.parseInt(request.getParameter("sight_no"));
    String sightName = request.getParameter("sight_name");
    String sightAddr = request.getParameter("sight_name");
    int sightId = Integer.parseInt(request.getParameter("sight_id"));
    String sightCategoryName = request.getParameter("sight_category_name");



  }
}
