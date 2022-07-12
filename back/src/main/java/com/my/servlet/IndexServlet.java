package com.my.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.my.dto.Client;
import com.my.dto.Diary;
import com.my.repository.DiaryOracleRepository;
import com.my.repository.DiaryRepository;
import com.my.sql.MyConnection;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<Diary> diaries = new ArrayList<Diary>();
    String envPath = getServletContext().getRealPath("project.properties");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DiaryRepository diaryRepository = new DiaryOracleRepository(envPath);


    try {
      con = MyConnection.getConnection(envPath);
      String SelectDiaryLikeSQL = "           SELECT *\n"
          + "            FROM (select * from diaries d\n"
          + "            LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\n"
          + "            WHERE d.diary_disclosure_flag =  1 and d.diary_delete_flag = 1 and c.client_status_flag = 1\n"
          + "            ORDER BY d.diary_like_cnt) \n" + "            where ROWNUM <= 10";
      pstmt = con.prepareStatement(SelectDiaryLikeSQL);
      rs = pstmt.executeQuery();
      int cnt = 0;
      while (rs.next() || cnt <= 5) {
        // diary
        int diary_no = rs.getInt("diary_no");
        String client_id = rs.getString("client_id");
        String diary_title = rs.getString("diary_title");
        Date diary_writing_time = rs.getDate("diary_writing_time");
        Date diary_start_date = rs.getDate("diary_start_date");
        Date diary_end_date = rs.getDate("diary_end_date");
        int diary_disclosure_flag = rs.getInt("diary_disclosure_flag");
        int diary_view_cnt = rs.getInt("diary_view_cnt");
        int diary_like_cnt = rs.getInt("diary_like_cnt");
        int diary_delete_flag = rs.getInt("diary_delete_flag");

        // client 용
        String client_pwd = rs.getString("client_pwd");
        String client_cellphone_no = rs.getString("client_cellphone_no");
        String client_nickname = rs.getString("client_nickname");
        int client_status_flag = rs.getInt("client_status_flag");
        Client client = new Client(client_id, client_pwd, client_cellphone_no, client_nickname,
            client_status_flag);


        Diary diary =
            new Diary(diary_no, diary_title, diary_writing_time, diary_start_date, diary_end_date,
                diary_disclosure_flag, diary_view_cnt, diary_like_cnt, diary_delete_flag, client);
        diaries.add(diary);
        cnt += 1;
      }

    } catch (Exception e) {
      e.getMessage();
    }

  }

}

/*
 * html 파일 이름 : index.html java 파일 이름 : IndexServlet.java 백엔드 url : /back/index get/post 여부 : post
 * MIME : application/json parameter : List diaries (좋아요 가장 많은 4개의 다이어리), List diaries (최신 10개의
 * 다이어리) attribute : X
 * 
 * -- 좋아요 상위 N개 뽑기 SELECT diary_no,like_cnt,diary_writting_time FROM (SELECT * FROM diaries ORDER BY
 * like_cnt DESC) WHERE ROWNUM <= N;
 * 
 * -- 최신글 상위 N개 뽑기 SELECT diary_no FROM(SELECT * FROM diaries ORDER BY diary_writting_time DESC )
 * WHERE ROWNUM <= N;
 */
