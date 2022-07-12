package com.my.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Admin;
import com.my.exception.SelectException;
import com.my.repository.AdminOracleRepository;
import com.my.repository.AdminRepository;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;


  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    String admin_id = request.getParameter("admin_id");
    String admin_pwd = request.getParameter("admin_pwd");
    AdminRepository adminrepo = new AdminOracleRepository(envPath);
    try {
      Admin admin = adminrepo.selectAdminByIdAndPwd(admin_id, admin_pwd);
    } catch (SelectException e) {
      e.printStackTrace();
    }
    response.setContentType("application/json;charset=UTF-8");
  }

}
