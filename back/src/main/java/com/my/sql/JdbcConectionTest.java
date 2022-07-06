package com.my.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class JdbcConectionTest {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();

    String driver = dotenv.get("DB_DRIVER");

    // String url = "jdbc:oracle:thin:@전자지갑이름성능?TNS_ADMIN=/전자지갑파일경로/전자지갑파일";
    String url = dotenv.get("DB_URL");
    String id = dotenv.get("DB_ID");
    String pwd = dotenv.get("DB_PWD");

    Connection con = null;

    try {



      Class.forName(driver);
      con = DriverManager.getConnection(url, id, pwd);
      System.out.println("데이터베이스 연결 성공!!");

    } catch (Exception e) {

      System.out.println("데이터베이스 연결 실패!!");
      e.printStackTrace();

    } finally {
      try {
        if (con != null)
          con.close();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
