package com.routediary.service;

import com.routediary.dto.Client;
import com.routediary.exception.AddException;
import com.routediary.exception.DuplicationException;
import com.routediary.exception.FindException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.exception.WithdrawnClientException;


public interface ClientService {
  /**
   * 회원가입을 한다.
   *
   * @param client
   * @throws AddException
   */
  public void signup(Client client) throws AddException;

  /**
   * 로그인을 한다.(로그인 시 아이디와 비밀번호가 맞지 않는다면, 또는 탈퇴한 회원이라면 로그인 실패) 로그인에 성공하면 true를 반환
   * 
   * @param clientId
   * @param clientPwd
   * @throws FindException
   * @throws WithdrawnClientException
   * @throws MismatchException
   * @return boolean
   */
  public boolean login(String clientId, String clientPwd)
      throws FindException, MismatchException, WithdrawnClientException;

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
   * 아이디 중복체크를 한다. 성공시 true를 반환
   *
   * @param clientId
   * @throws FindException
   * @return boolean
   * @throws DuplicationException
   */
  public boolean idDuplicationCheck(String clientId) throws FindException, DuplicationException;

  /**
   * 닉네임 중복체크를 한다. 성공시 true를 반환
   *
   * @param clientNickname
   * @throws SelectException
   * @return boolean
   * @throws DuplicationException
   */
  public boolean nicknameDuplicationCheck(String clientNickname)
      throws FindException, DuplicationException;

  public Client bringClientInfo(String clientId) throws FindException;

}
