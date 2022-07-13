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
import com.my.dto.Diary;
import com.my.dto.Like;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.repository.LikeOracleRepository;
import com.my.repository.LikeRepository;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LikeServlet() {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    LikeRepository likeRepository = new LikeOracleRepository(envPath);
    response.setContentType("application/jasn;charset=UTF-8");
    PrintWriter out = response.getWriter();

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();

    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");
    // 프론트에서 받는 파라미터 (like = 1 - 좋아요증가 / 0 = 좋아요감소)
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));
    int likeFlag = Integer.parseInt(request.getParameter("like_flag"));


    Diary diary = new Diary();
    Like like = new Like();
    like.setClientId(clientId);
    like.setDiaryNo(diaryNo); // 현재 보고있는 다이어리 페이지에서 가져옴

    try {
      if (likeFlag == 1) {
        likeRepository.insertLike(like);
        map.put("status", 1); // like 증가
        map.put("message", clientId + "의 좋아요 증가");
      } else if (likeFlag == 0) {
        likeRepository.deleteLike(like);
        map.put("status", 0); // like 감소
        map.put("message", clientId + "의 좋아요 감소");
      }
    } catch (InsertException e) {
      map.put("status", -1); // like 증가, 감소 자체가 안되는 경우
      map.put("message", "좋아요/좋아요취소 작업 실패. " + e.getMessage());
      e.printStackTrace();
    } catch (DeleteException e) {
      map.put("status", -1); // like 증가, 감소 자체가 안되는 경우
      map.put("message", "좋아요/좋아요취소 작업 실패. " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", -1); // like 증가, 감소 자체가 안되는 경우
      map.put("message", e.getMessage());
      e.printStackTrace();
    }

    String result = mapper.writeValueAsString(map);
    out.print(result);

  }

}
