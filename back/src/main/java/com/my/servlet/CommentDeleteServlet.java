package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Comment;
import com.my.exception.DeleteException;
import com.my.repository.CommentOracleRepository;
import com.my.repository.CommentRepository;

@WebServlet("/commentdelete")
public class CommentDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String envPath = getServletContext().getRealPath("project.properties");
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter out = response.getWriter();

    try {
      Comment comment = CommentRepository.selectDiraryByDiaryNo(comment);
      commentRepository.delete(comment);
    } catch (DeleteException e) {
      e.printStackTrace();
    }
  }

}

