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
import com.my.dto.Client;
import com.my.exception.SelectException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LoginServlet() {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String clientId = request.getParameter("client_id");
    String clientPwd = request.getParameter("client_pwd");
    int clientStatusFlag = Integer.parseInt(request.getParameter("client_status_flag"));

    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Client client = null;
    Map<String, Object> map = new HashMap<String, Object>();
    HttpSession session = request.getSession();

    try {
      client = clientRepository.selectClientByIdAndPwd(clientId, clientPwd);
      if (clientStatusFlag == 0 || client == null) {
        map.put("status", 0);
        map.put("message", "로그인 실패 : ID와 패스워드를 다시 확인해 주세요.");
      } else {
        map.put("status", 1);
        map.put("message", "로그인 성공");
        session.setAttribute("loginInfo", clientId);
        // System.out.print(session.isNew() + "<br>");
        // System.out.print(session.getId() + "<br>");
        // System.out.print(session.getLastAccessedTime() + "<br>");
      }
    } catch (SelectException e) {
      map.put("status", 0);
      map.put("message", "로그인 실패 : ID와 패스워드를 다시 확인해 주세요.");
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);

  }

}
