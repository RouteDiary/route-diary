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
import com.my.exception.UpdateException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;

@WebServlet("/clientdelete")
public class ClientDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");
    try {
      Client client = clientRepository.selectClientById(clientId);
      client.setClientStatusFlag(0);
      clientRepository.update(client);
      session.invalidate();
      map.put("status", 1);
      map.put("message", "회원탈퇴 성공");
    } catch (SelectException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (UpdateException e) {
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
