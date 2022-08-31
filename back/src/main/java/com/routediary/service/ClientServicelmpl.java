package com.routediary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.routediary.dto.Client;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.InsertException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;
import com.routediary.repository.ClientRepository;


@Service
public class ClientServicelmpl implements ClientService{
  
  @Autowired
  private ClientRepository clientRepository;
  
  @Override
  public void signup(Client client) throws AddException{
    try {
      clientRepository.insert(client);
    } catch (InsertException e) {
      e.printStackTrace();
      System.out.println("이미 회원가입 된 아이디 입니다.:" + e.getMessage());
    }
  }
  
  @Override
  public void login(String clientId) throws FindException{ 
    try {
      Client client = clientRepository.selectClientById(clientId);
    } catch (SelectException e) {
      e.printStackTrace();
    }
  
  }
  
  @Override
  public void modifyAccount(Client client) throws ModifyException{
    try {
      clientRepository.update(client);
    } catch (UpdateException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void removeAccount(Client client) throws UpdateException{
    clientRepository.update(client);
  }
  
  @Override
  public void idDuplicationCheck(String clientId) throws SelectException, FindException{
    Client client = clientRepository.selectClientById(clientId);
    if(client != null) {
      throw new FindException("중복");
    }
  
  }
  
  @Override
  public void NicknameDuplicationCheck(String clientNickname) throws SelectException, FindException{
    Client client = clientRepository.selectClientByNickname(clientNickname);
    if(client != null) {
      throw new FindException("중복");
      }
  }
}
