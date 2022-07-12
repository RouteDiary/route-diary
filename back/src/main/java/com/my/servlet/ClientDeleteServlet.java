package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.my.dto.Client;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;

/**
 * Servlet implementation class ClientDeleteServlet
 */
@WebServlet("/clientdelete")
public class ClientDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");
    try {
      Client client = clientRepository.selectClientById(clientId);
      client.setClientStatusFlag(0);
      clientRepository.update(client);
    } catch (SelectException e) {
      e.printStackTrace();
    } catch (UpdateException e) {
      e.printStackTrace();
    }

  }

}
