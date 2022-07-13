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
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    CommentRepository commentRepository = new CommentOracleRepository(envPath);
    ClientRepository clientRepository = new ClientOracleRepository(envPath);
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    int commentNo = Integer.parseInt(request.getParameter("comment_no"));

    String clientId = (String) session.getAttribute("login_info");
    Comment comment = new Comment();
    try {
      Client client = clientRepository.selectClientById(clientId);
      System.out.println("clientId : " + clientId);
      comment.setDiaryNo(diaryNo);
      comment.setCommentNo(commentNo);
      commentRepository.delete(comment);
      System.out.println("삭제된 코멘트 : " + comment);
      map.put("status", 1);
      map.put("message", "댓글 삭제 성공");
    } catch (DeleteException e) {
      map.put("status", 0);
      map.put("message", e.getMessage());
      e.printStackTrace();
    } catch (SelectException e) {
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
