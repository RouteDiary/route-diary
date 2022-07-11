package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.my.dto.Sight;
import com.my.exception.InsertException;
import com.my.sql.MyConnection;

public class SightOracleRepository implements SightRepository {
  private String envPath;

  public SightOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  @Override
  public void insert(Sight sight) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String insertSQL = "INSERT INTO sights \r\n" + "     VALUES (sight_no_seq.NEXTVAL\r\n"
          + "           , ? \r\n" + "           , ? \r\n" + "           , ? \r\n"
          + "           , ? \r\n" + ")";
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setString(1, sight.getSightName());
      pstmt.setString(2, sight.getSightAddr());
      pstmt.setInt(3, sight.getSightId());
      pstmt.setString(4, sight.getSightCategoryName());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new InsertException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
