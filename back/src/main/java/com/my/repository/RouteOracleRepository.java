package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.my.dto.Route;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.sql.MyConnection;

public class RouteOracleRepository implements RouteRepository {
  private String envPath;

  public RouteOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  @Override
  public int selectRoutesRowSizeBydiaryNo(int diaryNo) throws SelectException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int routesSize = 0;
    try {
      con = MyConnection.getConnection(envPath);
      String sizeSql = "SELECT COUNT(*) FROM routes WHERE diary_no = ? ";
      pstmt = con.prepareStatement(sizeSql);
      pstmt.setInt(1, diaryNo);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        routesSize = rs.getInt("COUNT(*)");
      }
    } catch (SQLException e) {
      throw new SelectException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return routesSize;
  }

  @Override
  public void insert(List<Route> routes) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String insertSQL = "INSERT INTO ROUTES \r\n" + "     VALUES ( ? \r\n" + "           , ? \r\n"
          + "           , ? \r\n" + "           , ? )";
      pstmt = con.prepareStatement(insertSQL);
      int routesSize = routes.size();
      for (int i = 0; i < routesSize; i++) {
        pstmt.setInt(1, routes.get(i).getDiaryNo());
        pstmt.setInt(2, i + 1);
        pstmt.setString(3, routes.get(i).getRouteContent());
        pstmt.setString(4, routes.get(i).getKakaoMapId());
        pstmt.addBatch();
      }
      pstmt.executeBatch();
    } catch (SQLException e) {
      throw new InsertException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }

  @Override
  public void delete(int diaryNo, int routesRowSizeByDiaryNo) throws DeleteException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String deleteSQL =
          "DELETE FROM routes \r\n" + "      WHERE diary_no = ? \r\n" + "        AND route_no = ? ";
      pstmt = con.prepareStatement(deleteSQL);
      for (int i = 0; i < routesRowSizeByDiaryNo; i++) {
        pstmt.setInt(1, diaryNo);
        pstmt.setInt(2, i + 1);
        pstmt.addBatch();
      }
      pstmt.executeBatch();

    } catch (SQLException e) {
      throw new DeleteException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
