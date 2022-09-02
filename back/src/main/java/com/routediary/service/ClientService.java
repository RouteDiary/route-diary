package com.routediary.service;

import org.springframework.stereotype.Service;
import com.routediary.dto.Client;
import com.routediary.exception.AddException;
import com.routediary.exception.ClientException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;

@Service
public interface ClientService {
  /**
   * 회원가입을 한다.
   * @param client
   * @throws AddException
   */
  void signup(Client client) throws AddException;
  
  /**
   * 로그인을 한다.(로그인 시 아이디와 비밀번호가 틀리다면, "로그인 실패 !!.")
   * @param clientId, clientPwd
   * @throws FindException
   */
  Client login(String clientId,String clientPwd) throws FindException, ClientException;
  
  /**
   * 회원 정보를 수정한다.
   * @param client
   * @throws ModifyException
   */
  void modifyAccount(Client client) throws ModifyException;
  
  /**
   * 회원 계정을 탈퇴한다.(계정탈퇴 시, 각 다이어리에 좋아요, 댓글, 작성 모두 삭제된다.)
   * @param client
   * @throws RemoveException
   */
  void removeAccount(Client client) throws RemoveException;
  /**
   * 아이디 중복체크를 한다.(중복된 아이디가 있으면, "아이디가 중복(초복,말복)입니다.")
   * @param clientId
   * @throws FindException 
   */
  void idDuplicationCheck(String clientId) throws FindException;
  
  /**
   * 닉네임 중복체크를 한다.(중복된 닉네임이 있으면, "닉네임 중복(초복,말복)입니다.")
   * @param clientNickname
   * @throws SelectException
   */
  void NicknameDuplicationCheck(String clientNickname) throws FindException;
  
}
