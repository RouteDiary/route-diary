package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import com.my.dto.Client;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;
import com.my.sql.MyConnection;

public class ClientOracleRepository implements ClientRepository {

  private String envPath;

  public ClientOracleRepository(String envPath) {
    this.envPath = envPath;
  }

  //
  // 로그인 완료!
  @Override
  public Client selectByIdAndPwd(String clientId, String clientPwd) throws SelectException {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Client client = null;
    try {
      con = MyConnection.getConnection(envPath);
      String selectByIdAndPwdSQL =
          "SELECT * FROM clients WHERE client_id= ? AND client_pwd= ? AND client_status_flag = 1";
      pstmt = con.prepareStatement(selectByIdAndPwdSQL);
      pstmt.setString(1, clientId);
      pstmt.setString(2, clientPwd);
      pstmt.executeQuery();
      rs = pstmt.executeQuery();
      if (rs.next() == true) {
        client = setClientData(rs);
      } else {
        throw new SelectException("반환되는 열이 없습니다");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(rs, pstmt, con);
    }
    return client;
  }

  @Override
  // 회원가입 완료!
  public void insert(Client client) throws InsertException {
    Connection con = null;
    PreparedStatement pstmt = null;
    String signUpClientSQL =
        "INSERT INTO clients(client_id" + ",client_pwd" + ",client_cellphone_no"
            + ",client_nickname" + ",client_status_flag)" + "VALUES (?,?,?,?,1)";
    try {
      con = MyConnection.getConnection(envPath);
      pstmt = con.prepareStatement(signUpClientSQL);
      pstmt.setString(1, client.getClientId());
      pstmt.setString(2, client.getClientPwd());
      pstmt.setString(3, client.getClientCellphoneNo());
      pstmt.setString(4, client.getClientNickname());
      pstmt.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
      throw new InsertException(e.getMessage());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }



  @Override
  /*
   * 정보수정+탈퇴 완료!
   */
  public void update(Client client) throws UpdateException {
    Connection con = null;
    PreparedStatement pstmt = null;
    String updateSQL = "UPDATE clients " + "SET client_pwd = ? " + ",client_cellphone_no = ?"
        + ",client_nickname = ?" + ",client_status_flag = ? " + "WHERE client_id = ?";
    try {
      con = MyConnection.getConnection(envPath);
      pstmt = con.prepareStatement(updateSQL);
      pstmt.setString(1, client.getClientPwd());
      pstmt.setString(2, client.getClientCellphoneNo());
      pstmt.setString(3, client.getClientNickname());
      pstmt.setInt(4, client.getClientStatusFlag());
      pstmt.setString(5, client.getClientId());
      pstmt.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
      throw new UpdateException(e.getMessage());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      MyConnection.close(pstmt, con);
    }
  }

  private Client setClientData(ResultSet rs) throws SQLException, ParseException {
    Client client = new Client();
    client.setClientId(rs.getString("client_id"));
    client.setClientPwd(rs.getString("client_pwd"));
    client.setClientCellphoneNo(rs.getString("client_cellphone_no"));
    client.setClientNickname(rs.getString("client_nickname"));
    client.setClientStatusFlag(Integer.parseInt(rs.getString("client_status_flag")));
    return client;
  }


}
