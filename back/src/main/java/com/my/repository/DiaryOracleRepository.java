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
import com.my.dto.Comment;
import com.my.dto.Diary;
import com.my.dto.Route;
import com.my.dto.Sight;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;
import com.my.sql.MyConnection;

public class DiaryOracleRepository implements DiaryRepository {

  private String envPath;

  public DiaryOracleRepository(String envPath) {
    this.envPath = envPath;
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

  private Route setRouteData(ResultSet rs) throws SQLException, ParseException {
    Route route = new Route();
    route.setDiaryNo(Integer.parseInt(rs.getString("diary_no")));
    route.setRouteNo(Integer.parseInt(rs.getString("route_no")));
    route.setRouteContent(rs.getString("route_content"));
    return route;
  }

  private Sight setSightData(ResultSet rs) throws SQLException, ParseException {
    Sight sight = new Sight();
    sight.setSightNo(Integer.parseInt(rs.getString("sight_no")));
    sight.setSightName(rs.getString("sight_name"));
    sight.setSightAddr(rs.getString("sight_addr"));
    sight.setSightId(Integer.parseInt(rs.getString("sight_id")));
    sight.setSightCategoryName(rs.getString("sight_category_name"));
    return sight;
  }

  private Comment setCommentData(ResultSet rs) throws SQLException, ParseException {
    Comment comment = new Comment();
    comment.setDiaryNo(Integer.parseInt(rs.getString("diary_no")));
    comment.setCommentNo(Integer.parseInt(rs.getString("comment_no")));
    comment.setCommentContent(rs.getString("comment_content"));
    comment.setCommentWritingTime(
        new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("comment_writing_time")));
    return comment;
  }

  @Override
  public List<Diary> selectDirariesByWritingDate(int diaryStartNo, int diaryEndNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

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
      pstmt.setInt(1, diaryStartNo);
      pstmt.setInt(2, diaryEndNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }

      if (diaries.size() == 0) {
        throw new SelectException("선택된 다이어리가 없습니다.");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDirariesByViewCnt(int diaryStartNo, int diaryEndNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

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
      pstmt.setInt(1, diaryStartNo);
      pstmt.setInt(2, diaryEndNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }

      if (diaries.size() == 0) {
        throw new SelectException("선택된 다이어리가 없습니다.");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDirariesByLikeCnt(int diaryStartNo, int diaryEndNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

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
      pstmt.setInt(1, diaryStartNo);
      pstmt.setInt(2, diaryEndNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }

      if (diaries.size() == 0) {
        throw new SelectException("선택된 다이어리가 없습니다.");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDirariesById(String clientId, int diaryStartNo, int diaryEndNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

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
      pstmt.setInt(2, diaryStartNo);
      pstmt.setInt(3, diaryEndNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }

      if (diaries.size() == 0) {
        throw new SelectException("선택된 다이어리가 없습니다.");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return diaries;
  }

  @Override
  public List<Diary> selectDirariesByKeyword(String keyword, int diaryStartNo, int diaryEndNo)
      throws SelectException {
    List<Diary> diaries = new ArrayList<Diary>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

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
          + "                LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)\r\n"
          + "                LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)\r\n"
          + "                          WHERE diary_disclosure_flag = 1\r\n"
          + "                            AND diary_delete_flag = 1                        \r\n"
          + "                            AND (r.route_content LIKE '%' || ? || '%' \r\n"
          + "                                 OR d.diary_title LIKE '%' || ? || '%' \r\n"
          + "                                 OR s.sight_name LIKE '%' || ? || '%' )\r\n"
          + "                        ORDER BY diary_writing_time DESC)\r\n"
          + "     )            \r\n" + " WHERE rnum BETWEEN ? AND ?";
      pstmt = con.prepareStatement(selectSQL);
      pstmt.setString(1, keyword);
      pstmt.setString(2, keyword);
      pstmt.setString(3, keyword);
      pstmt.setInt(4, diaryStartNo);
      pstmt.setInt(5, diaryEndNo);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Diary diary = setDiaryData(rs);
        Client client = setClientData(rs);
        diary.setClient(client);
        diaries.add(diary);
      }

      if (diaries.size() == 0) {
        throw new SelectException("선택된 다이어리가 없습니다.");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return diaries;
  }

  private Diary BringDiaryDataByDiaryNo(Connection con, ResultSet rs, PreparedStatement pstmt,
      int diaryNo) throws SelectException, SQLException, ParseException {
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
      int diaryNo) throws SelectException, SQLException, ParseException {
    List<Route> routes = new ArrayList<Route>();
    String selectSQL = "SELECT *\r\n" + "           FROM routes r\r\n"
        + "LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)\r\n"
        + "          WHERE diary_no = ? ";
    pstmt = con.prepareStatement(selectSQL);
    pstmt.setInt(1, diaryNo);
    rs = pstmt.executeQuery();
    Route route = null;
    Sight sight = null;
    while (rs.next()) {
      route = setRouteData(rs);
      sight = setSightData(rs);
      route.setSight(sight);
      routes.add(route);
    }
    if (routes.size() == 0) {
      throw new SelectException("diaryNo에 해당하는 route가 없습니다.");
    }
    return routes;
  }

  private List<Comment> BringCommentDataByDiaryNo(Connection con, ResultSet rs,
      PreparedStatement pstmt, int diaryNo) throws SelectException, SQLException, ParseException {
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
    if (comments.size() == 0) {
      throw new SelectException("diaryNo에 해당하는 comment가 없습니다.");
    }
    return comments;
  }

  @Override
  public Diary selectDiraryByDiaryNo(int diaryNo) throws SelectException {
    Diary diary = new Diary();
    List<Route> routes = new ArrayList<Route>();
    List<Comment> comments = new ArrayList<Comment>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

      routes = BringRouteDataByDiaryNo(con, rs, pstmt, diaryNo);
      comments = BringCommentDataByDiaryNo(con, rs, pstmt, diaryNo);
      diary = BringDiaryDataByDiaryNo(con, rs, pstmt, diaryNo);
      diary.setRoutes(routes);
      diary.setComments(comments);
    } catch (ParseException e) {
      e.printStackTrace();
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
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

      String updateSQL =
          "UPDATE diaries  \r\n" + "   SET diary_view_cnt = ? \r\n" + " WHERE diary_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setInt(1, diary.getDiaryViewCnt() + 1);
      pstmt.setInt(2, diary.getDiaryNo());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new UpdateException(e.getMessage());
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
  }

  @Override
  public void insert(Diary diary) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

      String insertSQL = "INSERT INTO diaries \r\n" + "     VALUES (diary_no_seq.NEXTVAL\r\n"
          + "           , ? \r\n" + "           , ? \r\n"
          + "           , TO_DATE(SYSDATE, 'yyyy/mm/dd')\r\n" + "           , ? \r\n"
          + "           , ? \r\n" + "           , 1\r\n" + "           , 0\r\n"
          + "           , 0\r\n" + "           , 1\r\n" + ")";
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setString(1, diary.getClient().getClientId());
      pstmt.setString(2, diary.getDiaryTitle());
      pstmt.setDate(3, new java.sql.Date(diary.getDiaryStartDate().getTime()));
      pstmt.setDate(4, new java.sql.Date(diary.getDiaryEndDate().getTime()));
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new InsertException(e.getMessage());
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }

  @Override
  public void update(Diary diary) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

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
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
