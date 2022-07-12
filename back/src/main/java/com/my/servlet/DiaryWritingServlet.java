package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.my.exception.InsertException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

@WebServlet("/diarywriting")
public class DiaryWritingServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public DiaryWritingServlet() {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();

    HttpSession session = request.getSession();
    String clientId = (String) session.getAttribute("login_info");
    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);

    try {
      DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
      String diaryTitle = request.getParameter("diary_title");
      Date diaryStartDate = formatter.parse("diary_start_date");
      Date diaryEndDate = formatter.parse("diary_end_date");
      Diary diary = new Diary();
      diary.setDiaryTitle(diaryTitle);
      diary.setDiaryStartDate(diaryStartDate);
      diary.setDiaryEndDate(diaryEndDate);

      diaryRepository.insert(diary);
      session.setAttribute("diary", diary);

      map.put("status", 1);
      map.put("message", "DiaryOracleRepository.insert() 성공");
      // 실험용 코드 start (시험 끝나고 지워도 됨)
      int diaryNo = diary.getDiaryNo();
      System.out.println(diaryNo);
      // 실험용 코드 end (시험 끝나고 지워도 됨)
    } catch (ParseException e) {
      map.put("status", 0);
      map.put("message", "DiaryOracleRepository.insert() 실패");
      e.printStackTrace();
    } catch (InsertException e) {
      map.put("status", 0);
      map.put("message", "DiaryOracleRepository.insert() 실패");
      e.printStackTrace();
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
