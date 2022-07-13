package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.my.dto.Route;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.UpdateException;
import com.my.sql.MyConnection;

public class RouteOracleRepository implements RouteRepository {
  private String envPath;

  public RouteOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  @Override
  public void insert(Route route) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String insertSQL = "INSERT INTO ROUTES \r\n" + "     VALUES (? \r\n"
          + "           , (SELECT NVL(MAX(route_no), 0) + 1\r\n"
          + "          FROM routes WHERE diary_no = ? )\r\n" + "           , ? \r\n"
          + "           , ? ) ";
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setInt(1, route.getDiaryNo());
      pstmt.setInt(2, route.getDiaryNo());
      pstmt.setString(3, route.getRouteContent());
      pstmt.setString(4, route.getKakaoMapId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new InsertException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }

  @Override
  public void update(Route route) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String updateSQL = "UPDATE routes \r\n" + "   SET route_content = ? \r\n"
          + "     , kakao_map_id = ? \r\n" + " WHERE diary_no = ? \r\n" + "   AND route_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setString(1, route.getRouteContent());
      pstmt.setString(2, route.getKakaoMapId());
      pstmt.setInt(3, route.getDiaryNo());
      pstmt.setInt(4, route.getRouteNo());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new UpdateException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }

  @Override
  public void delete(Route route) throws DeleteException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String updateSQL =
          "DELETE FROM routes \r\n" + "      WHERE diary_no = ? \r\n" + "        AND route_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setInt(1, route.getDiaryNo());
      pstmt.setInt(2, route.getRouteNo());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DeleteException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
