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
import com.my.exception.InsertException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<>();

    String clientId = request.getParameter("client_id");
    String clientPwd = request.getParameter("client_pwd");
    String clientCellphoneNo = request.getParameter("client_cellphone_no");
    String clientNickname = request.getParameter("client_nickname");
    int clientStatusFlag = Integer.parseInt(request.getParameter("client_status_flag"));
    Client client =
        new Client(clientId, clientPwd, clientCellphoneNo, clientNickname, clientStatusFlag);
    try {
      clientRepository.insert(client);
      map.put("status", 1);
      map.put("messgage", "가입성공");

    } catch (InsertException e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("messgage", "가입실패 : " + e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("messgage", "가입실패 : " + e.getMessage());
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
