package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.routediary.dto.Client;

@Repository
@Mapper
public interface ClientRepository {

  /**
   * 회원 ID를 조회한다 (로그인할때, 중복체크할때)
   *
   * @param clientId
   * @return Client
   */
  public Client selectClientById(String clientId);

  /**
   * 회원 닉네임을 조회한다(중복체크할때)
   *
   * @param clientNickname
   * @return Client
   */
  public Client selectClientByNickname(String clientNickname);

  /**
   * Client(회원) 객체를 DB에 추가
   *
   * @param client
   */
  public void insert(Client client);

  /**
   * 1. 회원정보를 수정 - clientPwd, clientNickname, clientCellphoneNo 2. 회원탈퇴 - clientStatusFlag = 1 ->
   * 가입상태, 0 -> 탈퇴상태로 변경
   *
   * @param client
   */
  public void update(Client client);
}
