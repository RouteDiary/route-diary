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
import com.my.dto.Comment;
import com.my.exception.InsertException;
import com.my.repository.CommentOracleRepository;
import com.my.repository.CommentRepository;

@WebServlet("/commentinsert")
public class CommentInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<>();
    HttpSession session = request.getSession();
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    String clientId = request.getParameter("client_id");
    String commentContent = request.getParameter("comment_content");
    Comment comment = new Comment();
    comment.setDiaryNo(diaryNo);
    comment.getClient().setClientId(clientId);
    comment.setCommentContent(commentContent);

    try {
      commentRepository.insert(comment);
      map.put("status", 1);
    } catch (InsertException e) {
      e.printStackTrace();
      map.put("status", 0);
    }
    out.print(mapper.writeValueAsString(map));
  }

}
