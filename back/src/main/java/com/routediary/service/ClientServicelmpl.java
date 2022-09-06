package com.routediary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.routediary.dto.Client;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.repository.ClientRepository;


@Service(value = "ClientService")
public class ClientServicelmpl implements ClientService {

  @Autowired
  public ClientRepository clientRepository;

  @Override
  public void signup(Client client) throws AddException {
    try {
      clientRepository.insert(client);
    } catch (Exception e) {
      e.printStackTrace();
      throw new AddException("회원정보를 DB에 추가하는데 실패하였습니다. \n" + e.getMessage());
    }
  }

  @Override
  public void login(String clientId, String clientPwd) throws FindException {
    try {
      Client client = clientRepository.selectClientById(clientId);
      if (!clientPwd.equals(client.getClientPwd())) {
        throw new FindException("아이디 혹은 비밀번호가 일치하지 않습니다.");
      } else if (client.getClientStatusFlag() == 0) {
        throw new FindException("이미 탈퇴한 회원입니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("서버 오류 : " + e.getMessage());
    }

  }

  @Override
  public void modifyAccount(Client client) throws ModifyException {
    try {
      clientRepository.update(client);
    } catch (Exception e) {
      throw new ModifyException("회원정보 수정에 실패하였습니다. " + e.getMessage());
    }
  }

  @Override
  public void removeAccount(Client client) throws RemoveException {
    try {
      clientRepository.update(client);
    } catch (Exception e) {
      throw new RemoveException("회원탈퇴에 실패하였습니다. " + e.getMessage());
    }
  }

  @Override
  // 이메일 형식, 글자수 제한 체크 로직도 필요함
  public void idDuplicationCheck(String clientId) throws FindException {
    try {
      Client client = clientRepository.selectClientById(clientId);
      if (client != null) {
        throw new FindException("아이디 중복");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("서버오류 : " + e.getMessage());
    }
  }

  @Override
  // 닉네임 글자 제한로직도 추가해야함
  public void NicknameDuplicationCheck(String clientNickname) throws FindException {
    try {
      Client client = clientRepository.selectClientByNickname(clientNickname);
      if (client != null) {
        throw new FindException("닉네임 중복");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FindException("서버오류 : " + e.getMessage());
    }
  }
}

