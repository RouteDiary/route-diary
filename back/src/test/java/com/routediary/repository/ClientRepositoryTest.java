package com.routediary.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Client;

@SpringBootTest
class ClientRepositoryTest {

  @Autowired
  ClientRepository clientRepository;

  @Test
  void SelectClientByIdTest() throws Exception {
    // login을 위한 테스트
    String clientId = "koreawoman@gmail.com";
    String expectedClientPwd = "asd15";
    String expectedClientNickname = "한국남자5";
    String expectedClientCellphoneNo = "010-1115-2234";
    Integer expectedClientStatusFlag = new Integer(0);
    Client client = clientRepository.selectClientById(clientId);
    assertEquals(clientId, client.getClientId());
    assertEquals(expectedClientPwd, client.getClientPwd());
    assertEquals(expectedClientNickname, client.getClientNickname());
    assertEquals(expectedClientCellphoneNo, client.getClientCellphoneNo());
    assertEquals(expectedClientStatusFlag, client.getClientStatusFlag());

    // id 중복체크를 위한 테스트
    String clientId2 = "newacc@gmail.com";
    Client client2 = clientRepository.selectClientById(clientId2);
    assertNull(client2);
  }

  @Test
  void SelectClientByNicknameTest() throws Exception {
    // nickname 중복체크를 위한 테스트
    String clientNickname = "새별명";
    Client client = clientRepository.selectClientByNickname(clientNickname);
    assertNull(client);
  }

  @Test
  void InsertTest() throws Exception {
    String clientId = "kmj1@naver.com";
    String clientPwd = "kmj0801";
    String clientCellphoneNo = "010-1234-5798";
    String clientNickname = "밍쥬여신님";
    Integer expectedClientStatusFlag = new Integer(1);
    Client client = new Client();
    // 회원가입시 들어갈 정보
    client.setClientId(clientId);
    client.setClientPwd(clientPwd);
    client.setClientCellphoneNo(clientCellphoneNo);
    client.setClientNickname(clientNickname);
    clientRepository.insert(client);

    Client clientInDb = clientRepository.selectClientById(clientId);
    assertEquals(clientId, clientInDb.getClientId());
    assertEquals(clientPwd, clientInDb.getClientPwd());
    assertEquals(clientNickname, clientInDb.getClientNickname());
    assertEquals(clientCellphoneNo, clientInDb.getClientCellphoneNo());
    assertEquals(expectedClientStatusFlag, clientInDb.getClientStatusFlag());
  }

  @Test
  void UpdateTest() throws Exception {
    // 회원정보를 수정하는 경우
    String expectedClientId = "kmj1@naver.com";
    String expectedClientPwd = "kmj0310";
    String expectedClientNickname = "밍쥬겅듀";
    String expectedClientCellphoneNo = "010-1111-2222";
    Integer expectedClientStatusFlag = new Integer(1);
    Client client1 = new Client();
    client1.setClientId(expectedClientId);
    client1.setClientPwd(expectedClientPwd);
    client1.setClientNickname(expectedClientNickname);
    client1.setClientCellphoneNo(expectedClientCellphoneNo);
    clientRepository.update(client1);
    Client updatedClient = clientRepository.selectClientById(expectedClientId);
    assertEquals(expectedClientId, updatedClient.getClientId());
    assertEquals(expectedClientPwd, updatedClient.getClientPwd());
    assertEquals(expectedClientNickname, updatedClient.getClientNickname());
    assertEquals(expectedClientCellphoneNo, updatedClient.getClientCellphoneNo());
    assertEquals(expectedClientStatusFlag, updatedClient.getClientStatusFlag());
  }

  @Test
  void UpdateForWithdrawalTest() throws Exception {
    // 회원탈퇴하는 경우
    String expectedClientId2 = "koreaman@gmail.com";
    String expectedClientPwd2 = "asd11";
    String expectedClientNickname2 = "한쿡";
    String expectedClientCellphoneNo2 = "010-2392-2948";
    Integer expectedClientStatusFlag2 = new Integer(0);
    Client client2 = new Client();
    client2.setClientId(expectedClientId2);
    client2.setClientStatusFlag(expectedClientStatusFlag2);
    clientRepository.update(client2);
    Client updatedClient = clientRepository.selectClientById(expectedClientId2);
    assertEquals(expectedClientStatusFlag2, updatedClient.getClientStatusFlag());
    assertEquals(expectedClientId2, updatedClient.getClientId());
    assertEquals(expectedClientPwd2, updatedClient.getClientPwd());
    assertEquals(expectedClientNickname2, updatedClient.getClientNickname());
    assertEquals(expectedClientCellphoneNo2, updatedClient.getClientCellphoneNo());
  }
}
