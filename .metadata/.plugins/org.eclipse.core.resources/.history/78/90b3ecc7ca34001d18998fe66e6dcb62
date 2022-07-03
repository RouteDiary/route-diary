package com.routediary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.routediary.dto.Client;
import com.routediary.enums.ErrorCode;
import com.routediary.exception.AddException;
import com.routediary.exception.DuplicationException;
import com.routediary.exception.FindException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.exception.WithdrawnClientException;
import com.routediary.repository.ClientRepository;


@Service(value = "ClientService")
public class ClientServicelmpl implements ClientService {

  @Autowired
  public ClientRepository clientRepository;

  @Override
  public void signup(Client client) throws AddException {
    clientRepository.insert(client);
  }

  @Override
  public boolean login(String clientId, String clientPwd)
      throws FindException, MismatchException, WithdrawnClientException {

    Client client = clientRepository.selectClientById(clientId);
    if (!clientPwd.equals(client.getClientPwd())) {
      throw new MismatchException(ErrorCode.ID_PWD_MISMATCH);
    } else if (client.getClientStatusFlag() == 0) {
      throw new WithdrawnClientException(ErrorCode.ALREADY_WITHDRAWN_CLIENT);
    } else {
      return true;
    }
  }

  @Override
  public void modifyAccount(Client client) throws ModifyException {
    clientRepository.update(client);
  }

  @Override
  public void removeAccount(Client client) throws RemoveException {
    clientRepository.update(client);
  }

  @Override
  // 이메일 형식, 글자수 제한 체크 로직도 필요함
  public boolean idDuplicationCheck(String clientId) throws FindException, DuplicationException {
    Client client = clientRepository.selectClientById(clientId);
    if (client != null) {
      throw new DuplicationException(ErrorCode.ID_DUPLICATION);
    } else {
      return true;
    }
  }

  @Override
  // 닉네임 글자 제한로직도 추가해야함
  public boolean nicknameDuplicationCheck(String clientNickname)
      throws FindException, DuplicationException {
    Client client = clientRepository.selectClientByNickname(clientNickname);
    if (client != null) {
      throw new DuplicationException(ErrorCode.ID_DUPLICATION);
    } else {
      return true;
    }
  }

  @Override
  public Client bringClientInfo(String clientId) throws FindException {
    Client client = clientRepository.selectClientById(clientId);
    if (client == null) {
      throw new FindException();
    } else {
      return client;
    }
  }
}

