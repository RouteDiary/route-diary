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
import com.my.exception.SelectException;
import com.my.exception.UpdateException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

@WebServlet("/diarydelete")
public class DiaryDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DiaryDeleteServlet() {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));

    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      Diary diary = diaryRepository.selectDiaryByDiaryNo(diaryNo);
      if (clientId.equals(diary.getClient().getClientId())) {
        map.put("status", 1);
        map.put("message", "다이어리가 삭제되었습니다.");
        diary.setDiaryDeleteFlag(0);
        diaryRepository.update(diary);
      } else {
        map.put("status", 0);
        map.put("message", "다른 유저의 다이어리는 삭제할 수 없습니다.");
      }
    } catch (SelectException e) {
      e.printStackTrace();
    } catch (UpdateException e) {
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
