package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.my.dto.Comment;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.UpdateException;
import com.my.sql.MyConnection;

public class CommentOracleRepository implements CommentRepository {

  private String envPath;

  public CommentOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  @Override
  public void insert(Comment comment) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String insertSQL = " INSERT INTO comments VALUES ( ? ,(SELECT NVL(MAX(comment_no), 0) + 1 "
          + "FROM comments WHERE diary_no = ? ), ? , ? , SYSDATE)";
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setInt(1, comment.getDiaryNo());
      pstmt.setInt(2, comment.getDiaryNo());
      pstmt.setString(3, comment.getClient().getClientId());
      pstmt.setString(4, comment.getCommentContent());
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
  public void update(Comment comment) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String updateSQL = "UPDATE comments set comment_content= ? , comment_writing_time=SYSDATE "
          + "WHERE diary_no= ? AND comment_no= ? ";
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setString(1, comment.getCommentContent());
      pstmt.setInt(2, comment.getDiaryNo());
      pstmt.setInt(3, comment.getCommentNo());
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
  public void delete(Comment comment) throws DeleteException {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = MyConnection.getConnection(envPath);
      String deleteSQL = "DELETE FROM comments WHERE diary_no= ? AND comment_no= ? ";
      pstmt = con.prepareStatement(deleteSQL);
      pstmt.setInt(1, comment.getDiaryNo());
      pstmt.setInt(2, comment.getCommentNo());
    } catch (SQLException e) {
      throw new DeleteException(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }
}
