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
import com.my.exception.UpdateException;
import com.my.repository.ClientOracleRepository;
import com.my.repository.ClientRepository;
import com.my.repository.CommentOracleRepository;
import com.my.repository.CommentRepository;

/**
 * Servlet implementation class CommentUpdateServlet
 */
@WebServlet("/commentupdate")
public class CommentUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    // String clientId = (String) session.getAttribute("client_id");
    // System.out.println(clientId);
    // PrintWriter out = response.getWriter();
    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    // String sqlClientId = request.getParameter("client_id");
    String commentContent = request.getParameter("comment_content");
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    int commentNo = Integer.parseInt(request.getParameter("comment_no"));
    Client client = null;
    try {
      Comment comment = new Comment();
      // System.out.println(clientId);

      // client = clientRepository.selectClientById(clientId);
      // System.out.println(clientId);

      // String dbclientId = client.getClientId();
      // System.out.println(dbclientId);

      // if (clientId == dbclientId) {
      comment.setClient(client);
      comment.setCommentContent(commentContent);
      comment.setDiaryNo(diaryNo);
      comment.setCommentNo(commentNo);
      commentRepository.update(comment);
      request.setAttribute("message", "수정완료");
      // }
      // } catch (SelectException e) {
      // e.printStackTrace();
    } catch (UpdateException e) {
      e.printStackTrace();
    }

  }
}



// String clientId = request.getParameter("client_id");

// Diary diary = null;
// Client client = null;

// Diary diary = DiaryOracleRepository.selectDiraryByDiaryNo(diaryNo);
// Comment comment = new Comment(diaryNo, commentNo, commentContent, client);

// diary.setDiaryNo(diaryNo);
// comment.setCommentNo(commentNo);
// client.setClientId(clientId);
