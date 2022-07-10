package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.my.dto.Admin;
import com.my.exception.SelectException;
import com.my.sql.MyConnection;

public class AdminOracleRepository implements AdminRepository {
  private String envPath;

  public AdminOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  @Override
  public Admin selectByIdAndPwd(String adminId, String adminPwd) throws SelectException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Admin admin = new Admin();
    try {
      con = MyConnection.getConnection(envPath);
      String selectByIdAndPwdSQL = "SELECT * FROM admins WHERE admin_id= ? AND admin_pwd= ?";
      pstmt = con.prepareStatement(selectByIdAndPwdSQL);
      pstmt.setString(1, adminId);
      pstmt.setString(2, adminPwd);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        admin.setAdminId(rs.getString("admin_id"));
        admin.setAdminPwd(rs.getString("admin_pwd"));
      } else {
        throw new SelectException("해당되는 관리자가 없습니다.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return admin;
  }

}

