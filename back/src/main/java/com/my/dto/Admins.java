package com.my.dto;

/**
 * admin_id VARCHAR2(20 CHAR) NOT NULL, admin_pwd VARCHAR2(20 CHAR) NOT NULL, CONSTRAINT admins_pk
 * PRIMARY KEY(admin_id)
 *
 */
public class Admins {
  private String adminId;
  private String adminPwd;

  public Admins() {}

  public Admins(String adminId, String adminPwd) {
    this.adminId = adminId;
    this.adminPwd = adminPwd;
  }

  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public String getAdminPwd() {
    return adminPwd;
  }

  public void setAdminPwd(String adminPwd) {
    this.adminPwd = adminPwd;
  }

  @Override
  public String toString() {
    return "Admins [adminId=" + adminId + ", adminPwd=" + adminPwd + "]";
  }


}
