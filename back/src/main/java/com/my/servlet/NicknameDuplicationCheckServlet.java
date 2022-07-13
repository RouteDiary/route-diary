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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Client;
import com.my.exception.SelectException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;

/**
 * Servlet implementation class NicknameDuplicationCheckServlet
 */
@WebServlet("/nicknameduplicationcheck")
public class NicknameDuplicationCheckServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String clientNickname = request.getParameter("client_nickname");

    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    Client client = null;
    try {
      client = clientRepository.selectClientByNickname(clientNickname);
      if (client == null) {
        map.put("status", 1);
        map.put("message", "사용가능한 닉네임입니다.");
      } else {
        map.put("status", 0);
        map.put("message", "이미 사용중인 닉네임입니다.");
      }
    } catch (SelectException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
