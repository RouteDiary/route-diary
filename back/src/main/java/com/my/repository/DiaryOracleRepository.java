package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.my.dto.Client;
import com.my.dto.Diary;
import com.my.sql.MyConnection;

public class DiaryOracleRepository implements DiaryRepository {
  // 추가해야할 부분 START
  private String envPath;

  public DiaryOracleRepository(String envPath) {
    this.envPath = envPath;
  }
  // 추가해야할 부분 END

  @Override
  public List<Diary> selectDirariesByWritingDate() {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    try {
      try {
        con = MyConnection.getConnection(envPath);
        System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        selectFirstToTenthDirariesByWritingDate(con, diaries);
      } catch (ParseException e) {
        e.printStackTrace();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(null, con);
    }

    return diaries;
  }

  private void selectFirstToTenthDirariesByWritingDate(Connection con, List<Diary> diaries)
      throws SQLException, ParseException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String selectSQL = "         SELECT *\r\n" + "           FROM diaries d\r\n"
        + "LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
        + "          WHERE diary_disclosure_flag = 1 -- 공개글만 반환\r\n"
        + "            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 \r\n"
        + "            AND ROWNUM <= 10 -- 1~10번째 글만 반환\r\n"
        + "       ORDER BY diary_writing_time DESC";
    pstmt = con.prepareStatement(selectSQL);

    rs = pstmt.executeQuery();
    while (rs.next()) {

      Diary diary = setDiaryData(rs);
      Client client = setClientData(rs);
      System.out.println("diary table의 row 불러옴 : " + diary);
      diaries.add(diary);
    }
  }

  private Client setClientData(ResultSet rs) throws SQLException, ParseException {
    Client client = new Client();
    client.setClientId(rs.getString("client_id"));
    client.setClientPwd(rs.getString("client_pwd"));
    client.setClientCellphoneNo(rs.getString("client_cellphone_no"));
    client.setClientNickname(rs.getString("client_nickname"));
    client.setClientStatusFlag(Integer.parseInt(rs.getString("client_status_flag")));
    return client;
  }

  private Diary setDiaryData(ResultSet rs) throws SQLException, ParseException {
    Diary diary = new Diary();
    diary.setDiaryNo(Integer.parseInt(rs.getString("diary_no")));
    diary.setClientId(rs.getString("client_id"));
    diary.setDiaryTitle(rs.getString("diary_title"));
    diary.setDiaryWritingTime(
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("diary_writing_time")));
    diary.setDiaryStartDate(
        new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("diary_start_date")));
    diary.setDiaryEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("diary_end_date")));
    diary.setDiaryDisclosureFlag(Integer.parseInt(rs.getString("diary_disclosure_flag")));
    diary.setDiaryViewCnt(Integer.parseInt(rs.getString("diary_view_cnt")));
    diary.setDiaryLikeCnt(Integer.parseInt(rs.getString("diary_like_cnt")));
    diary.setDiaryDeleteFlag(Integer.parseInt(rs.getString("diary_delete_flag")));
    return diary;
  }

  @Override
  public List<Diary> selectDirariesByViewCnt() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Diary> selectDirariesByLikeCnt() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Diary> selectDirariesById(String clientId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Diary> selectDirariesByKeyword(String keyword) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Diary selectDiraryByDiaryNo(int diaryNo) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int updateViewCnt(int diaryNo) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void insert(Diary diary) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update(Diary diary) {
    // TODO Auto-generated method stub

  }

}
