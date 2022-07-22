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
    PrintWriter out = response.getWriter();
    response.setContentType("application/json;charset=UTF-8");
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    String envPath = getServletContext().getRealPath("project.properties");
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    HttpSession session = request.getSession();
    // String clientId = (String) session.getAttribute("client_id");
    String clientId = "a11";
    System.out.println("client_id:" + clientId);
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    System.out.println("diary_no:" + diaryNo);
    int commentNo = Integer.parseInt(request.getParameter("comment_no"));
    System.out.println("comment_no:" + commentNo);
    String commentContent = request.getParameter("comment_content");
    System.out.println("comment_content:" + commentContent);

    try {
      Client client = new Client();
      Comment comment = new Comment();
      comment.setDiaryNo(diaryNo);
      comment.setCommentNo(commentNo);
      comment.setClient(client);
      comment.setCommentContent(commentContent);
      commentRepository.update(comment);
      map.put("status", 1);
      map.put("message", "댓글 수정 성공");
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



// String clientId = request.getParameter("client_id");

// Diary diary = null;
// Client client = null;

// Diary diary = DiaryOracleRepository.selectDiraryByDiaryNo(diaryNo);
// Comment comment = new Comment(diaryNo, commentNo, commentContent, client);

// diary.setDiaryNo(diaryNo);
// comment.setCommentNo(commentNo);
// client.setClientId(clientId);
