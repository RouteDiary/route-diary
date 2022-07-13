package com.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Diary;
import com.my.exception.SelectException;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<>();
    String envPath = getServletContext().getRealPath("project.properties");
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);
    List<Diary> diariesOrderedByLikeCnt = new ArrayList<Diary>();
    List<Diary> diariesOrderedByWritingTime = new ArrayList<Diary>();
    int totalRows = 0;
    try {
      // 좋아요순, 최신순 모두 다이어리를 5개씩 가져오도록 설정해놨습니다. 필요한만큼 수정해서 쓰시면 됩니다.
      int diaryDisClosureFlag = 1;
      totalRows = diaryRepository.selectDiariesRowSize(diaryDisClosureFlag);
      if (totalRows >= 5) {
        diariesOrderedByLikeCnt = diaryRepository.selectDiariesByLikeCnt(1, 5);
        diariesOrderedByWritingTime = diaryRepository.selectDiariesByWritingTime(1, 5);
      } else {
        diariesOrderedByLikeCnt = diaryRepository.selectDiariesByLikeCnt(1, totalRows);
        diariesOrderedByWritingTime = diaryRepository.selectDiariesByWritingTime(1, totalRows);
      }
      map.put("status", 1);
      map.put("diaries_ordered_by_like_cnt", diariesOrderedByLikeCnt);
      map.put("diaries_ordered_by_writing_time", diariesOrderedByWritingTime);
      map.put("message", "좋아요순, 최신순 다이어리 가져오기 성공");
    } catch (SelectException e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("message", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      map.put("status", 0);
      map.put("message", e.getMessage());
    }
    String result = mapper.writeValueAsString(map);
    out.print(result);
  }
}
