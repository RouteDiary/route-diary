package com.my.repository;

import com.my.dto.Client;
import com.my.exception.InsertException;
import com.my.exception.SelectException;
import com.my.exception.UpdateException;

public interface ClientRepository {

  Client selectByIdAndPwd(String clientId, String clientPwd) throws SelectException;

  void insert(Client client) throws InsertException;

  void update(Client client) throws UpdateException;
}
