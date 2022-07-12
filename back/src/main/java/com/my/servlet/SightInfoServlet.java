package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Sight;
import com.my.exception.InsertException;
import com.my.repository.SightOracleRepository;
import com.my.repository.SightRepository;

@WebServlet("/sightinfo")
public class SightInfoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();
    SightRepository sightRepository = new SightOracleRepository(envPath);
    int sightNo = Integer.parseInt(request.getParameter("sight_no"));
    String sightName = request.getParameter("sight_name");
    String sightAddr = request.getParameter("sight_name");
    int sightId = Integer.parseInt(request.getParameter("sight_id"));
    String sightCategoryName = request.getParameter("sight_category_name");

    Sight sight = new Sight(sightNo, sightName, sightAddr, sightId, sightCategoryName);

    try {
      sightRepository.insert(sight);
    } catch (InsertException e) {
      e.printStackTrace();
    }
    out.print(sight);
  }
}
