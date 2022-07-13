package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.my.dto.Client;
import com.my.dto.Comment;
import com.my.dto.Diary;
import com.my.dto.Route;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;
import com.my.sql.MyConnection;

public class DiaryOracleRepository implements DiaryRepository {

  private String envPath;

  public DiaryOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  private Client setClientData(ResultSet rs) throws SQLException {
    Client client = new Client();
    client.setClientId(rs.getString("client_id"));
    client.setClientPwd(rs.getString("client_pwd"));
    client.setClientCellphoneNo(rs.getString("client_cellphone_no"));
    client.setClientNickname(rs.getString("client_nickname"));
    client.setClientStatusFlag(rs.getInt("client_status_flag"));
    return client;
  }

  private Diary setDiaryData(ResultSet rs) throws SQLException {
    Diary diary = new Diary();
    diary.setDiaryNo(rs.getInt("diary_no"));
    diary.setDiaryTitle(rs.getString("diary_title"));
    diary.setDiaryWritingTime(rs.getDate("diary_writing_time"));
    diary.setDiaryStartDate(rs.getDate("diary_start_date"));
    diary.setDiaryEndDate(rs.getDate("diary_end_date"));
    diary.setDiaryDisclosureFlag(rs.getInt("diary_disclosure_flag"));
    diary.setDiaryViewCnt(rs.getInt("diary_view_cnt"));
    diary.setDiaryLikeCnt(rs.getInt("diary_like_cnt"));
    diary.setDiaryDeleteFlag(rs.getInt("diary_delete_flag"));
    return diary;
  }

  private Route setRouteData(ResultSet rs) throws SQLException {
    Route route = new Route();
    route.setDiaryNo(rs.getInt("diary_no"));
    route.setRouteNo(rs.getInt("route_no"));
    route.setRouteContent(rs.getString("route_content"));
    route.setKakaoMapId(rs.getString("kakao_map_id"));
    return route;
  }

  private Comment setCommentData(ResultSet rs) throws SQLException {
    Comment comment = new Comment();
    comment.setDiaryNo(rs.getInt("diary_no"));
    comment.setCommentNo(rs.getInt("comment_no"));
    comment.setCommentContent(rs.getString("comment_content"));
    comment.setCommentWritingTime(rs.getDate("comment_writing_time"));
    return comment;
  }

  @Override
  public int selectDiariesRowSize(int diaryDisclosureFlag) throws SelectException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int diariesSize = 0;
    try {
      con = MyConnection.getConnection(envPath);
      String sizeSql =
          "SELECT COUNT(*) FROM diaries WHERE diary_delete_flag = 1 AND diary_disclosure_flag = ? ";
      pstmt = con.prepareStatement(sizeSql);
      pstmt.setInt(1, diaryDisclosureFlag);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        diariesSize = rs.getInt("COUNT(*)");
      }
    } catch (SQLException e) {
      throw new SelectException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return diariesSize;
  }


  @Override
  public List<Diary> selectDiariesByWritingDate(int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);

      String selectSQL = "SELECT * \r\n" + "  FROM  (SELECT d.*\r\n"
          + "              , client_pwd\r\n" + "              , client_cellphone_no\r\n"
          + "              , client_nickname\r\n" + "              , client_status_flag\r\n"
          + "              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum\r\n"
          + "           FROM diaries d\r\n"
          + "LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
          + "          WHERE diary_disclosure_flag = 1\r\n"
          + "            AND diary_delete_flag = 1\r\n"
          + "       ORDER BY diary_writing_time DESC)\r\n" + "WHERE rnum BETWEEN ? AND ? ";
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setInt(1, diaryStartRowNo);
      pstmt.setInt(2, diaryEndRowNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    if (diaries.size() == 0) {
      throw new SelectException("선택된 다이어리가 없습니다.");
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDiariesByViewCnt(int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);

      String selectSQL = "SELECT * \r\n" + "  FROM  (SELECT d.*\r\n"
          + "              , client_pwd\r\n" + "              , client_cellphone_no\r\n"
          + "              , client_nickname\r\n" + "              , client_status_flag\r\n"
          + "              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum\r\n"
          + "           FROM diaries d\r\n"
          + "LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
          + "          WHERE diary_disclosure_flag = 1\r\n"
          + "            AND diary_delete_flag = 1\r\n" + "       ORDER BY diary_view_cnt DESC,\r\n"
          + "                diary_writing_time DESC)\r\n" + "WHERE rnum BETWEEN ? AND ? ";
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setInt(1, diaryStartRowNo);
      pstmt.setInt(2, diaryEndRowNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    if (diaries.size() == 0) {
      throw new SelectException("선택된 다이어리가 없습니다.");
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDiariesByLikeCnt(int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);

      String selectSQL = "SELECT * \r\n" + "  FROM  (SELECT d.*\r\n"
          + "              , client_pwd\r\n" + "              , client_cellphone_no\r\n"
          + "              , client_nickname\r\n" + "              , client_status_flag\r\n"
          + "              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum\r\n"
          + "           FROM diaries d\r\n"
          + "LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
          + "          WHERE diary_disclosure_flag = 1\r\n"
          + "            AND diary_delete_flag = 1\r\n" + "       ORDER BY diary_like_cnt DESC,\r\n"
          + "                diary_writing_time DESC)\r\n" + "WHERE rnum BETWEEN ? AND ? ";
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setInt(1, diaryStartRowNo);
      pstmt.setInt(2, diaryEndRowNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    if (diaries.size() == 0) {
      throw new SelectException("선택된 다이어리가 없습니다.");
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDiariesById(String clientId, int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);

      String selectSQL = "SELECT * \r\n" + "  FROM (SELECT d.*\r\n"
          + "             , client_pwd\r\n" + "             , client_cellphone_no\r\n"
          + "             , client_nickname\r\n" + "             , client_status_flag\r\n"
          + "             , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum\r\n"
          + "          FROM diaries d\r\n"
          + "LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
          + "          WHERE d.client_id = ? \r\n" + "            AND diary_delete_flag = 1 \r\n"
          + "       ORDER BY diary_writing_time DESC)\r\n" + " WHERE rnum BETWEEN ? AND ? ";
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setString(1, clientId);
      pstmt.setInt(2, diaryStartRowNo);
      pstmt.setInt(3, diaryEndRowNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    if (diaries.size() == 0) {
      throw new SelectException("선택된 다이어리가 없습니다.");
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDiariesByKeyword(String keyword, int diaryStartRowNo, int diaryEndRowNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);

      String selectSQL = "SELECT DISTINCT diary_no\r\n" + "              , client_id \r\n"
          + "              , diary_title\r\n" + "              , diary_writing_time\r\n"
          + "              , diary_start_date\r\n" + "              , diary_end_date\r\n"
          + "              , diary_disclosure_flag\r\n" + "              , diary_view_cnt\r\n"
          + "              , diary_like_cnt\r\n" + "              , diary_delete_flag\r\n"
          + "              , client_pwd\r\n" + "              , client_cellphone_no\r\n"
          + "              , client_nickname\r\n" + "              , client_status_flag \r\n"
          + "              , rnum\r\n" + "           FROM (SELECT diary_no \r\n"
          + "                      , client_id \r\n" + "                      , diary_title\r\n"
          + "                      , diary_writing_time\r\n"
          + "                      , diary_start_date\r\n"
          + "                      , diary_end_date\r\n"
          + "                      , diary_disclosure_flag\r\n"
          + "                      , diary_view_cnt\r\n"
          + "                      , diary_like_cnt\r\n"
          + "                      , diary_delete_flag\r\n"
          + "                      , client_pwd\r\n"
          + "                      , client_cellphone_no\r\n"
          + "                      , client_nickname\r\n"
          + "                      , client_status_flag\r\n"
          + "                      , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum\r\n"
          + "                   FROM (SELECT d.diary_no\r\n"
          + "                              , d.client_id \r\n"
          + "                              , d.diary_title\r\n"
          + "                              , d.diary_writing_time\r\n"
          + "                              , d.diary_start_date\r\n"
          + "                              , d.diary_end_date\r\n"
          + "                              , d.diary_disclosure_flag\r\n"
          + "                              , d.diary_view_cnt\r\n"
          + "                              , d.diary_like_cnt\r\n"
          + "                              , d.diary_delete_flag\r\n"
          + "                              , c.client_pwd\r\n"
          + "                              , c.client_cellphone_no\r\n"
          + "                              , c.client_nickname\r\n"
          + "                              , c.client_status_flag\r\n"
          + "                           FROM diaries d\r\n"
          + "                LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)\r\n"
          + "                LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
          + "                          WHERE diary_disclosure_flag = 1\r\n"
          + "                            AND diary_delete_flag = 1                        \r\n"
          + "                            AND (r.route_content LIKE '%' || ? || '%' \r\n"
          + "                                 OR d.diary_title LIKE '%' || ? || '%' \r\n"
          + "                                )\r\n"
          + "                        ORDER BY diary_writing_time DESC)\r\n"
          + "     )            \r\n" + " WHERE rnum BETWEEN ? AND ?";
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setString(1, keyword);
      pstmt.setString(2, keyword);
      pstmt.setInt(3, diaryStartRowNo);
      pstmt.setInt(4, diaryEndRowNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    if (diaries.size() == 0) {
      throw new SelectException("선택된 다이어리가 없습니다.");
    }
    return diaries;
  }

  private Diary BringDiaryDataByDiaryNo(Connection con, ResultSet rs, PreparedStatement pstmt,
      int diaryNo) throws SelectException, SQLException {
    String selectSQL = "SELECT * \r\n" + "             FROM diaries d\r\n"
        + "  LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
        + "            WHERE diary_no = ? ";
    pstmt = con.prepareStatement(selectSQL);
    pstmt.setInt(1, diaryNo);
    rs = pstmt.executeQuery();
    Diary diary = null;
    Client client = null;
    if (rs.next()) {
      diary = setDiaryData(rs);
      client = setClientData(rs);
      diary.setClient(client);
    } else {
      throw new SelectException("찾으려는 다이어리가 없습니다. diaryNo를 다시 확인하세요.");
    }
    return diary;
  }

  private List<Route> BringRouteDataByDiaryNo(Connection con, ResultSet rs, PreparedStatement pstmt,
      int diaryNo) throws SelectException, SQLException {
    List<Route> routes = new ArrayList<Route>();
    String selectSQL =
        "SELECT *\r\n" + "           FROM routes r\r\n" + "          WHERE diary_no = ? ";
    pstmt = con.prepareStatement(selectSQL);
    pstmt.setInt(1, diaryNo);
    rs = pstmt.executeQuery();
    Route route = null;
    while (rs.next()) {
      route = setRouteData(rs);
      routes.add(route);
    }
    return routes;
  }

  private List<Comment> BringCommentDataByDiaryNo(Connection con, ResultSet rs,
      PreparedStatement pstmt, int diaryNo) throws SelectException, SQLException {
    List<Comment> comments = new ArrayList<Comment>();
    String selectSQL = "SELECT *\r\n" + "           FROM comments co\r\n"
        + "LEFT OUTER JOIN clients cl ON (co.client_id = cl.client_id)\r\n"
        + "          WHERE diary_no = ? ";
    pstmt = con.prepareStatement(selectSQL);
    pstmt.setInt(1, diaryNo);
    rs = pstmt.executeQuery();
    Comment comment = null;
    Client client = null;
    while (rs.next()) {
      comment = setCommentData(rs);
      client = setClientData(rs);
      comment.setClient(client);
      comments.add(comment);
    }
    return comments;
  }

  @Override
  public Diary selectDiaryByDiaryNo(int diaryNo) throws SelectException {
    Diary diary = new Diary();
    List<Route> routes = new ArrayList<Route>();
    List<Comment> comments = new ArrayList<Comment>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      routes = BringRouteDataByDiaryNo(con, rs, pstmt, diaryNo);
      comments = BringCommentDataByDiaryNo(con, rs, pstmt, diaryNo);
      diary = BringDiaryDataByDiaryNo(con, rs, pstmt, diaryNo);
      diary.setRoutes(routes);
      diary.setComments(comments);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return diary;
  }

  @Override
  public void updateViewCnt(Diary diary) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      String updateSQL =
          "UPDATE diaries  \r\n" + "   SET diary_view_cnt = ? \r\n" + " WHERE diary_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setInt(1, diary.getDiaryViewCnt() + 1);
      pstmt.setInt(2, diary.getDiaryNo());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new UpdateException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
  }

  private int selectDiaryNoSeqNextval(Connection con, PreparedStatement pstmt, ResultSet rs)
      throws SQLException {
    String selectSQL = "SELECT diary_no_seq.NEXTVAL FROM dual";
    pstmt = con.prepareStatement(selectSQL);
    rs = pstmt.executeQuery(selectSQL);
    Long diaryNo = 0L;
    selectSQL = "SELECT diary_no_seq.CURRVAL FROM dual";
    rs = pstmt.executeQuery(selectSQL);
    if (rs.next()) {
      diaryNo = rs.getLong("CURRVAL");
    }
    return diaryNo.intValue();
  };

  @Override
  public void insert(Diary diary) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      int diaryNo = selectDiaryNoSeqNextval(con, pstmt, rs);
      diary.setDiaryNo(diaryNo);

      String insertSQL = "INSERT INTO diaries \r\n" + "     VALUES ( ? \r\n" + "           , ? \r\n"
          + "           , ? \r\n" + "           , TO_DATE(SYSDATE, 'yyyy/mm/dd')\r\n"
          + "           , ? \r\n" + "           , ? \r\n" + "           , ? \r\n"
          + "           , 0\r\n" + "           , 0\r\n" + "      , 1\r\n" + ")";
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setInt(1, diary.getDiaryNo());
      pstmt.setString(2, diary.getClient().getClientId());
      pstmt.setString(3, diary.getDiaryTitle());
      pstmt.setDate(4, new java.sql.Date(diary.getDiaryStartDate().getTime()));
      pstmt.setDate(5, new java.sql.Date(diary.getDiaryEndDate().getTime()));
      pstmt.setInt(6, diary.getDiaryDisclosureFlag());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new InsertException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
  }

  @Override
  public void update(Diary diary) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String updateSQL = "UPDATE diaries \r\n" + "   SET diary_title = ? \r\n"
          + "     , diary_writing_time = TO_DATE(SYSDATE, 'yyyy/mm/dd')\r\n"
          + "     , diary_start_date = ? \r\n" + "     , diary_end_date = ? \r\n"
          + "     , diary_disclosure_flag = ? \r\n" + "     , diary_delete_flag = ? \r\n"
          + " WHERE diary_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setString(1, diary.getDiaryTitle());
      pstmt.setDate(2, new java.sql.Date(diary.getDiaryStartDate().getTime()));
      pstmt.setDate(3, new java.sql.Date(diary.getDiaryEndDate().getTime()));
      pstmt.setInt(4, diary.getDiaryDisclosureFlag());
      pstmt.setInt(5, diary.getDiaryDeleteFlag());
      pstmt.setInt(6, diary.getDiaryNo());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new UpdateException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
