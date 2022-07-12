package com.my.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.my.dto.Client;
import com.my.dto.Comment;
import com.my.exception.DeleteException;
import com.my.exception.SelectException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;
import com.my.repository.CommentOracleRepository;
import com.my.repository.CommentRepository;

@WebServlet("/commentdelete")
public class CommentDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    // PrintWriter out = response.getWriter();
    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    int commentNo = Integer.parseInt(request.getParameter("comment_no"));

    String clientId = (String) session.getAttribute("login_info");
    Comment comment = new Comment();
    try {
      Client client = clientRepository.selectClientById(clientId);
      System.out.println(clientId);
      // Comment comment = commentRepository.delete(Comment);
      // String id = comment.getClient().getClientId();
      comment.setDiaryNo(diaryNo);
      comment.setCommentNo(commentNo);
      commentRepository.delete(comment);
      System.out.println(comment);
      // request.setAttribute("message", "댓글삭제완료");

    } catch (DeleteException e) {
      e.printStackTrace();
    } catch (SelectException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
