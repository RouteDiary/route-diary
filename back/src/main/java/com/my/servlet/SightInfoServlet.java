package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.exception.SelectException;
import com.my.repository.SightOracleRepository;
import com.my.repository.SightRepository;

@WebServlet("/back/sightinfo")
public class SightInfoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String envPath = getServletContext().getRealPath("project.properties");
    SightRepository sightRepository = new SightOracleRepository(envPath);
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();

    String sight = request.getParameter(sight);

    try {
      sight = sightRepository.selectOneSightDetail(sight.getSightNo());
    } catch (SelectException e) {
      e.printStackTrace();
    }
    out.print(sight);
  }
}
