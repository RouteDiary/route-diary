package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
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
   * 
   * @param clientId
   * @return Client
   * @throws SelectException
   */
  public Client selectClientById(String clientId) throws SelectException;

  /**
   * 회원 닉네임을 조회한다(중복체크할때)
   * 
   * @param clientNickname
   * @return Client
   * @throws SelectException
   */
  public Client selectClientByNickname(String clientNickname) throws SelectException;

  /**
   * Client(회원) 객체를 DB에 추가
   * 
   * @param client
   * @throws InsertException
   */
  public void insert(Client client) throws InsertException;

  /**
   * 1. 회원정보를 수정 - clientPwd, clientNickname, clientCellphoneNo 2. 회원탈퇴 - clientStatusFlag = 1 ->
   * 가입상태, 0 -> 탈퇴상태로 변경
   * 
   * @param client
   * @throw UpdateException
   */
  public void update(Client client) throws UpdateException;
}
