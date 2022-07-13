package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.my.exception.UpdateException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

@WebServlet("/diaryupdate")
public class DiaryUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String envPath = getServletContext().getRealPath("project.properties");
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<>();
    HttpSession session = request.getSession();
    int diaryNo = Integer.parseInt(request.getParameter("diary_no"));

    String diaryTitle = request.getParameter("diary_title");
    String diaryStartDate = request.getParameter("diary_start_date");
    String diaryEndDate = request.getParameter("diary_end_date");
    int diaryDisclosureFlag = Integer.parseInt(request.getParameter("diary_disclosure_flag"));
    Diary diary = new Diary();
    diary.setDiaryNo(diaryNo);
    diary.setDiaryTitle(diaryTitle);
    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
    Date startDate;
    Date endDate;
    try {
      startDate = sdf.parse(diaryStartDate);
      endDate = sdf.parse(diaryEndDate);
      diary.setDiaryStartDate(startDate);
      diary.setDiaryEndDate(endDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    diary.setDiaryDisclosureFlag(diaryDisclosureFlag);

    try {
      diaryRepository.update(diary);
      map.put("status", 1);
    } catch (UpdateException e) {
      e.printStackTrace();
      map.put("status", 0);
    }
    out.print(mapper.writeValueAsString(map));
  }

}
