package com.routediary.service;

import com.routediary.dto.Client;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;


public interface ClientService {
  /**
   * 회원가입을 한다.
   *
   * @param client
   * @throws AddException
   */
  public void signup(Client client) throws AddException;

  /**
   * 로그인을 한다.(로그인 시 아이디와 비밀번호가 맞지 않는다면, 또는 탈퇴한 회원이라면 로그인 실패)
   * 
   * @param clientId
   * @param clientPwd
   * @throws FindException
   */
  public void login(String clientId, String clientPwd) throws FindException;

  /**
   * 회원 정보를 수정한다.
   *
   * @param client
   * @throws ModifyException
   */
  public void modifyAccount(Client client) throws ModifyException;

  /**
   * 회원 계정을 탈퇴한다.(DB에서 정보를 삭제하는 것이 아닌 client_status_flag값을 1 -> 0으로 바꿈)
   *
   * @param client
   * @throws RemoveException
   */
  public void removeAccount(Client client) throws RemoveException;

  /**
   * 아이디 중복체크를 한다.
   *
   * @param clientId
   * @throws FindException
   */
  public void idDuplicationCheck(String clientId) throws FindException;

  /**
   * 닉네임 중복체크를 한다.
   *
   * @param clientNickname
   * @throws SelectException
   */
  public void NicknameDuplicationCheck(String clientNickname) throws FindException;

}
