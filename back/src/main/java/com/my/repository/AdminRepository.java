package com.my.repository;

import com.my.dto.Admin;
import com.my.exception.SelectException;

public interface AdminRepository {
  Admin selectByIdAndPwd(String adminId, String adminPwd) throws SelectException;

}
