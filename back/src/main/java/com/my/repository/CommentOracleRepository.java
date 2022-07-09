package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    String insertSQL = " INSERT INTO comments(diary_no, comment_no, client_id, comment_content"
        + ",comment_writing_time) VALUES (?,(SELECT NVL(MAX(comment_no), 0)+1 "
        + "FROM comments WHERE diary_no = ?),?,?, SYSDATE);";

    try {
      con = MyConnection.getConnection(envPath);
      System.out.println("envPath : " + envPath + "\nFrom CommentOracleRepository");
      pstmt = con.prepareStatement(insertSQL);
      pstmt.setInt(1, comment.getDiaryNo());
      pstmt.setInt(2, comment.getDiaryNo());
      pstmt.setString(3, comment.getClient().getClientId());
      pstmt.setString(4, comment.getCommentContent());
      pstmt.executeUpdate();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }


  }


  // "INSERT INTO order_info(ORDER_NO,ORDER_ID,ORDER_DT) VALUES (order_seq.NEXTVAL, ?, SYSDATE)";



  @Override
  public void update(Comment comment) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    String updateSQL = "UPDATE comments set comment_content=?, comment_writing_time=SYSDATE "
        + "WHERE diary_no=? AND comment_no=?";
    // UPDATE comments set comment_content='안~~녕', comment_writing_time=SYSDATE WHERE (diary_no=1
    // AND comment_no=1);
    try {

      con = MyConnection.getConnection(envPath);
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setString(1, comment.getCommentContent());
      pstmt.setInt(2, comment.getDiaryNo());
      pstmt.setInt(3, comment.getCommentNo());
      pstmt.executeUpdate();
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
    String deleteSQL = "DELETE FROM comments WHERE diary_no=? AND comment_no=?";
    try {
      con = MyConnection.getConnection(envPath);
      pstmt = con.prepareStatement(deleteSQL);
      pstmt.setInt(1, comment.getDiaryNo());
      pstmt.setInt(2, comment.getCommentNo());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }

}
