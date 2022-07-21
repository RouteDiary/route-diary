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

@WebServlet("/kakaosignup")
public class KakaoSignupServlet extends HttpServlet {
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
    String randomNickname = Integer.toString((int) Math.floor(Math.random() * 100000000));
    System.out.println(randomNickname);
    Client client = new Client(clientId, null, null, randomNickname, 1);
    try {
      clientRepository.insert(client);
      map.put("status", 1);
      map.put("message", "가입성공");

    } catch (InsertException e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("message", "가입실패 : " + e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("message", "가입실패 : " + e.getMessage());
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
