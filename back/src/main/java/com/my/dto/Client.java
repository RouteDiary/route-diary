package com.my.dto;

public class Client {

  private String clientId;
  private String clientPwd;
  private String clientCellphoneNo;
  private String clientNickname;
  private int clientStatusFlag;

  public Client() {}

  public Client(String clientId, String clientPwd, String clientCellphoneNo, String clientNickname,
      int clientStatusFlag) {
    this.clientId = clientId;
    this.clientPwd = clientPwd;
    this.clientCellphoneNo = clientCellphoneNo;
    this.clientNickname = clientNickname;
    this.clientStatusFlag = clientStatusFlag;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientPwd() {
    return clientPwd;
  }

  public void setClientPwd(String clientPwd) {
    this.clientPwd = clientPwd;
  }

  public String getClientCellphoneNo() {
    return clientCellphoneNo;
  }

  public void setClientCellphoneNo(String clientCellphoneNo) {
    this.clientCellphoneNo = clientCellphoneNo;
  }

  public String getClientNickname() {
    return clientNickname;
  }

  public void setClientNickname(String clientNickname) {
    this.clientNickname = clientNickname;
  }

  public int getClientStatusFlag() {
    return clientStatusFlag;
  }

  public void setClientStatusFlag(int clientStatusFlag) {
    this.clientStatusFlag = clientStatusFlag;
  }

  @Override
  public String toString() {
    return "Client [clientId=" + clientId + ", clientPwd=" + clientPwd + ", clientCellphoneNo="
        + clientCellphoneNo + ", clientNickname=" + clientNickname + ", clientStatusFlag="
        + clientStatusFlag + "]";
  }


}


