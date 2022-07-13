package com.my.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.my.dto.Route;
import com.my.exception.DeleteException;
import com.my.repository.RouteOracleRepository;
import com.my.repository.RouteRepository;

/**
 * Servlet implementation class ClientDeleteServlet
 */
@WebServlet("/routedelete")
public class RouteDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");

    RouteRepository routeRepository = new RouteOracleRepository(envPath);

    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    int routeNo = Integer.parseInt(request.getParameter("route_no"));
    // String routeContent = request.getParameter("route_content");
    // int sightNo = Integer.parseInt(request.getParameter("sight_no"));
    HttpSession session = request.getSession();
    // Sight sight = new Sight();

    // Route route = new Route(diaryNo, routeNo, routeContent, sight);
    // String result = "{\"status\":0, \"msg\": \"삭제성공\"}";

    try {
      Route route = new Route();
      route.setDiaryNo(diaryNo);
      route.setRouteNo(routeNo);
      System.out.println(diaryNo);
      routeRepository.delete(route);
      // session.setAttribute("", route);
    } catch (DeleteException e) {
      e.printStackTrace();
    }
    // PrintWriter out = response.getWriter();
    // out.print(result);


  }

}
