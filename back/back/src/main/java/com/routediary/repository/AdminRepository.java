package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Admin;

@Repository
@Mapper
public interface AdminRepository {
  /**
   * adminId(운영자아이디)로 검색한 Admin(운영자) 객체를 반환
   *
   * @author yongho
   * @param adminId
   * @return Admin
   */
  public Admin selectAdminById(String adminId);

}
