package com.my.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
  static {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() throws SQLException {
    Connection con = null;
    String url =
        "jdbc:oracle:thin:@semiprojectdb_high?TNS_ADMIN= /Users/minseong/Downloads/Wallet_semiprojectdb";
    String user = "admin";
    String password = "Kosta12345678";

    con = DriverManager.getConnection(url, user, password);
    return con;
  }

  public static void close(ResultSet rs, Statement stmt, Connection con) {
    try {
      try {
        if (rs != null) {
          rs.close();
        }
      } finally {
        try {
          if (stmt != null) {
            stmt.close();
          }
        } finally {
          if (con != null) {
            con.close();
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void close(Statement stat, Connection con) {
    // TODO Auto-generated method stub
    close(null, stat, con);
  }



}
