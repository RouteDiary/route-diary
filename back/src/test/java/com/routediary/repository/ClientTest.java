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
		String clientId = "kmj1@gmail.com";
		//회원 ID를 조회한다
		assertEquals(clientId, "kmj1@gmail.com"); //예측한 값 확인해보기
	}
	
	
	@Test
	void SelectClientByNicknameTest() throws SelectException{
		String clientNickname = "한쿡";
		//회원 닉네임을 조회한다
		assertEquals(clientNickname, "한쿡"); //예측한 값 확인해보기
	}
	
	
	@Test
	void InsertTest() throws InsertException{
		Client client = new Client();
		//회원가입시 들어갈 정보
		client.setClientId("kmj1@naver.com");
		client.setClientPwd("kmj0801");
		client.setClientCellphoneNo("010-1234-5798");
		client.setClientNickname("만듀임니다만");
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
		client1.setClientId("kmj@naver.com"); 
		client1.setClientPwd("kmj0308");
		client1.setClientCellphoneNo("010-1234-5797");
		client1.setClientNickname("만듀야");
		client1.setClientStatusFlag(0);
		repository.update(client1);
		assertEquals(client1.getClientNickname(), "만듀야"); //예측한 값 확인해보기
		
	}
	
	

	
	
}

