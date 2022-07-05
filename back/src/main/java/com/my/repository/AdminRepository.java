package com.my.repository;

import com.my.dto.Admin;

public interface AdminRepository {
  Admin selectByIdAndPwd(String adminId, String adminPwd);

}
