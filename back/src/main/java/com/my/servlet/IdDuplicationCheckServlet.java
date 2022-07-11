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

@WebServlet("/idduplicationcheck")
public class IdDuplicationCheckServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public IdDuplicationCheckServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String clientId = request.getParameter("client_id");

    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Client client = null;
    try {
      client = clientRepository.selectClientById(clientId);
      Map<String, Object> map = new HashMap<String, Object>();
      if (client == null) {
        map.put("status", 0);
        map.put("message", "사용가능한 ID입니다.");
      } else {
        map.put("status", 1);
        map.put("message", "이미 사용중인 ID입니다.");
      }
      String result = mapper.writeValueAsString(map);
      out.print(result);
    } catch (SelectException e) {
      e.printStackTrace();
    }
  }
}
