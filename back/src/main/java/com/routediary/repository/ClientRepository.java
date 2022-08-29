package com.routediary.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Client;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;

@Repository
@Mapper
public interface ClientRepository {
		
	/**
	 * 회원 ID를 조회한다 (로그인할때, 중복체크할때)
	 * @param clientId
	 * @return Client
	 * @throws FindException
	 */
	Client selectClientById(String clientId) throws SelectException;

	
	/**
	 * 회원 닉네임을 조회한다(중복체크할때)
	 * @param clientNickname
	 * @return Client
	 * @throws FindException
	 */
	 Client selectClientByNickname(String clientNickname) throws SelectException;
	
	
	/**
	 * 회원가입을 한다
	 * @param client
	 * @throws AddException
	 */
	public void insert(Client client) throws InsertException;


	/**
	 * 회원정보를 수정한다
	 * @param client
	 * @throw ModifyException
	 */
	public void update(Client client) throws UpdateException;
	
}
