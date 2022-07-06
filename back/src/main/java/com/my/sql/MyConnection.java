package com.my.sql;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MyConnection {
  public static Connection getConnection(String envPath) throws Exception {

    Connection con = null;
    // String url = "jdbc:oracle:thin:@전자지갑이름성능?TNS_ADMIN=/전자지갑파일경로/전자지갑파일";
    Properties env = new Properties();

    env.load(new FileInputStream(envPath));
    String driver = env.getProperty("DB_DRIVER");
    String url = env.getProperty("DB_URL");
    String id = env.getProperty("DB_ID");
    String pwd = env.getProperty("DB_PWD");
    Class.forName(driver);
    con = DriverManager.getConnection(url, id, pwd);
    System.out.println("MyConnection.java : db connection success!");
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
    close(null, stat, con);
  }



}
