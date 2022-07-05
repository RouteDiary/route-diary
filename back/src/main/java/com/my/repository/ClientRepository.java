package com.my.repository;

import com.my.dto.Client;

public interface ClientRepository {
  Client selectByIdAndPwd(String clientId, String clientPwd);

  void insert(Client client);

  void update(Client client);
}
