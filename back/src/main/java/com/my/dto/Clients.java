package com.my.dto;

/**
 * id VARCHAR2(20 CHAR) NOT NULL, pwd VARCHAR2(20 CHAR) NOT NULL, client_roadaddr VARCHAR2(20 CHAR)
 * NOT NULL, client_addr VARCHAR2(20 CHAR) NOT NULL, client_cellphone_no VARCHAR2(15) NOT NULL,
 * nickname VARCHAR2(10 CHAR) NOT NULL, status_flag NUMBER(1) NOT NULL, --가입 = 1 / 탈퇴 = 0 CONSTRAINT
 * clients_pk PRIMARY KEY(id), CONSTRAINT clients_uk UNIQUE(nickname)
 */
public class Clients {

  private String id;
  private String pwd;
  private String clientRoadaddr;
  private String clientAddr;
  private String clientCellphoneNo;
  private String nickname;
  private int statusFlag;

  public Clients() {}

  public Clients(String id, String pwd, String clientRoadaddr, String clientAddr,
      String clientCellphoneNo, String nickname, int statusFlag) {
    this.id = id;
    this.pwd = pwd;
    this.clientRoadaddr = clientRoadaddr;
    this.clientAddr = clientAddr;
    this.clientCellphoneNo = clientCellphoneNo;
    this.nickname = nickname;
    this.statusFlag = statusFlag;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getClientRoadaddr() {
    return clientRoadaddr;
  }

  public void setClientRoadaddr(String clientRoadaddr) {
    this.clientRoadaddr = clientRoadaddr;
  }

  public String getClientAddr() {
    return clientAddr;
  }

  public void setClientAddr(String clientAddr) {
    this.clientAddr = clientAddr;
  }

  public String getClientCellphoneNo() {
    return clientCellphoneNo;
  }

  public void setClientCellphoneNo(String clientCellphoneNo) {
    this.clientCellphoneNo = clientCellphoneNo;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public int getStatusFlag() {
    return statusFlag;
  }

  public void setStatusFlag(int statusFlag) {
    this.statusFlag = statusFlag;
  }

  @Override
  public String toString() {
    return "Clients [id=" + id + ", pwd=" + pwd + ", clientRoadaddr=" + clientRoadaddr
        + ", clientAddr=" + clientAddr + ", clientCellphoneNo=" + clientCellphoneNo + ", nickname="
        + nickname + ", statusFlag=" + statusFlag + "]";
  }
}


