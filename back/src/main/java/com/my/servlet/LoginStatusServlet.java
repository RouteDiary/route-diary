package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LoginStatusServlet
 */
@WebServlet("/loginstatus")
public class LoginStatusServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    String loginedId = (String) session.getAttribute("login_info");
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<>();
    if (loginedId == null) {
      map.put("status", 0);
    } else {
      map.put("status", 1);
    }
    response.setContentType("aplication/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.print(mapper.writeValueAsString(map));
  }
}

