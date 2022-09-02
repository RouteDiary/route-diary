package com.routediary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.routediary.dto.Client;
import com.routediary.exception.AddException;
import com.routediary.exception.ClientException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.repository.ClientRepository;


@Service
public class ClientServicelmpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public void signup(Client client) throws AddException {
    try {
      clientRepository.insert(client);
    } catch (Exception e) {
      throw new AddException("회원가입에 실패했습니다. " + e.getMessage());

    }
  }

  @Override
  public Client login(String clientId, String clientPwd) throws FindException, ClientException {
    try {
      Client client = clientRepository.selectClientById(clientId);
      if (!clientPwd.equals(client.getClientPwd())) {
        throw new ClientException("아이디 혹은 비밀번호가 일치하지 않습니다.");
      }
      return client;
    } catch (Exception e) {
      throw new FindException("회원 정보를 찾을수 없습니다." + e.getMessage());
    }

  }

  @Override
  public void modifyAccount(Client client) throws ModifyException {
    try {
      clientRepository.update(client);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void removeAccount(Client client) throws RemoveException {
    clientRepository.update(client);
  }

  @Override
  public void idDuplicationCheck(String clientId) throws FindException {
    Client client = clientRepository.selectClientById(clientId);
    if (client != null) {
      throw new FindException("아이디 중복");
    }

  }

  @Override
  public void NicknameDuplicationCheck(String clientNickname) throws FindException {
    Client client = clientRepository.selectClientByNickname(clientNickname);
    if (client != null) {
      throw new FindException("닉네임 중복");
    }
  }
}
