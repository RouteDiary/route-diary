package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.my.dto.Diary;
import com.my.sql.MyConnection;

public class DiaryOracleRepository implements DiaryRepository {

  @Override
  public List<Diary> selectDirariesByWritingDate() {
    Connection con = null;
    try {
      con = MyConnection.getConnection();
      selectFirstToTenthDirariesByWritingDate(con);
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(null, con);
    }

  }

  private void selectFirstToTenthDirariesByWritingDate(Connection con) throws SQLException {
    PreparedStatement pstmt = null;
    String selectSQL =
        "         SELECT d.diary_no -- 나중에 뺄것\r\n" + "              , d.diary_title\r\n"
            + "              , d.diary_writing_time\r\n" + "              , d.client_id \r\n"
            + "              , c.client_nickname\r\n" + "              , d.diary_start_date\r\n"
            + "              , d.diary_end_date\r\n" + "              , d.diary_view_cnt\r\n"
            + "              , d.diary_like_cnt\r\n" + "           FROM diaries d\r\n"
            + "LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
            + "          WHERE diary_disclosure_flag = 1 -- 공개글만 반환\r\n"
            + "            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 \r\n"
            + "            AND ROWNUM <= 10 -- 1~10번째 글만 반환\r\n"
            + "       ORDER BY diary_writing_time DESC";
    pstmt = con.prepareStatement(selectSQL);
    pstmt.executeUpdate();
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
