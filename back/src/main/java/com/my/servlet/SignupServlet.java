package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Client;
import com.my.exception.InsertException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    response.setContentType("application/json;charset=UTF-8");


    String clientId = request.getParameter("client_id");
    String clientPwd = request.getParameter("client_pwd");
    String clientCellphoneNo = request.getParameter("client_cellphone_no");
    String clientNickname = request.getParameter("client_nickname");
    int clientStatusFlag = Integer.parseInt(request.getParameter("client_status_flag"));
    Client client =
        new Client(clientId, clientPwd, clientCellphoneNo, clientNickname, clientStatusFlag);

    String result = "{\"status\":0, \"msg\": \"가입실패\"}";

    try {
      clientRepository.insert(client);
      result = "{\"status\":1, \"msg\": \"가입성공\"}";

    } catch (InsertException e) {
      e.printStackTrace();
    }

    PrintWriter out = response.getWriter();

    out.print(result);
  }

}
