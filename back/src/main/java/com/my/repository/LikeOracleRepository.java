package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.my.dto.Like;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.sql.MyConnection;


public class LikeOracleRepository implements LikeRepository {
  private String envPath;

  public LikeOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  @Override
  public void insertLike(Like like) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String insertLikeSQL = "INSERT INTO likes \r\n" + " VALUES ( ? \r\n" + ", ? )";
      pstmt = con.prepareStatement(insertLikeSQL);
      pstmt.setInt(1, like.getDiaryNo());
      pstmt.setString(2, like.getClientId());
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
  public void deleteLike(Like like) throws DeleteException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String deleteLikeSQL = "DELETE FROM likes WHERE diary_no = ? AND client_id = ? ";
      pstmt = con.prepareStatement(deleteLikeSQL);
      pstmt.setInt(1, like.getDiaryNo());
      pstmt.setString(2, like.getClientId());
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
