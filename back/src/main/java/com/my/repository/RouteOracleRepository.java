package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import com.my.dto.Route;
import com.my.dto.Sight;
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
      String insertSQL = "INSERT INTO ROUTES \r\n" + "     VALUES ( ? \r\n"
          + "           , (SELECT NVL(MAX(route_no), 0) + 1\r\n"
          + "          FROM routes WHERE diary_no = ? )\r\n" + "           , ? \r\n"
          + "           , ? )";
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setInt(1, route.getDiaryNo());
      pstmt.setInt(2, route.getDiaryNo());
      pstmt.setInt(3, route.getSight().getSightNo());
      pstmt.setString(4, route.getRouteContent());
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
  public void update(Route route) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String updateSQL = "UPDATE routes \r\n" + "   SET sight_no = ? \r\n"
          + "     , route_content = ? \r\n" + " WHERE diary_no = ? \r\n" + "   AND route_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      Sight sight = route.getSight();
      pstmt.setInt(1, sight.getSightNo());
      pstmt.setString(2, route.getRouteContent());
      pstmt.setInt(3, route.getDiaryNo());
      pstmt.setInt(4, route.getRouteNo());
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

  @Override
  public void delete(Route route) throws DeleteException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom DiaryOracleRepository");

      String updateSQL =
          "DELETE FROM routes \r\n" + "      WHERE diary_no = ? \r\n" + "        AND route_no = ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setInt(1, route.getDiaryNo());
      pstmt.setInt(2, route.getRouteNo());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DeleteException(e.getMessage());
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
