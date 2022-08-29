package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.routediary.dto.Admin;
import com.routediary.exception.SelectException;


@Repository
@Mapper
public interface AdminRepository {
	
	public Admin selectAdminById(String adminId) throws SelectException;
	/***
	 * 특정한 아이디로 검색된 운영자 객체 반환
	 * @author yongho
	 * @param adminId : 운영자아이디
	 * @return adminId 로 검색된 운영자 객체 반환
	 */
}
