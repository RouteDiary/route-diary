package com.routediary.respository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.routediary.dto.Client;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.exception.UpdateException;
import com.routediary.repository.ClientRepository;

@SpringBootTest
class ClientTest {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	ClientRepository repository;

	@Test
	void SelectClientByIdTest() throws SelectException{
		String clientId = "kkk@gmail.com";
		//회원 ID를 조회한다
		Optional<Client> optId = Optional.ofNullable(repository.selectClientById(clientId));
		assertTrue(optId.isPresent());
		
	}
	
	@Test
	void SelectClientByNicknameTest() throws SelectException{
		String clientNickname = "한쿡";
		//회원 닉네임을 조회한다
		Optional<Client> optNickname = Optional.ofNullable(repository.selectClientByNickname(clientNickname));
		assertTrue(optNickname.isPresent());	
	}
	
	@Test
	void InsertTest() throws InsertException{
		Client client = new Client();
		//회원가입시 들어갈 정보
		client.setClientId("kmj@naver.com");
		client.setClientPwd("kmj0803");
		client.setClientCellphoneNo("010-1234-5797");
		client.setClientNickname("만듀요");
		client.setClientStatusFlag(1); //not null 컬럼이라 null값을 받아오지못함
		repository.insert(client);

	}
	
	@Test
	void UpdateTest() throws UpdateException, InsertException{
		Client client1 = new Client();
//		client1.setClientPwd("kmj0803");
//		client1.setClientCellphoneNo("010-1234-5797");
//		client1.setClientNickname("만듀일까");
//		client1.setClientStatusFlag(0);
//		repository.insert(client1);
		
		// 업데이트될 정보
		client1.setClientId("kmj@naver.com"); //not null 컬럼이라 null값을 받아오지못함
		client1.setClientPwd("kmj0308");
		client1.setClientCellphoneNo("010-1234-5797");
		client1.setClientNickname("만듀임");
		client1.setClientStatusFlag(0);
		repository.update(client1);
		assertEquals(client1.getClientNickname(), "만듀임"); //왜 같은지 확인?
		
	}
	
}

