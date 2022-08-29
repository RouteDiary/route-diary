package com.routediary.respository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.routediary.dto.Admin;
import com.routediary.exception.InsertException;
import com.routediary.exception.SelectException;
import com.routediary.repository.AdminRepository;

@SpringBootTest
class AdminTest {
	@Autowired
	AdminRepository repository;
	
	@Test
	void testselectAdminById() throws SelectException{
	Admin admin = new Admin();
	admin.setAdminId("abc");
	assertEquals(admin.getAdminId(), repository.selectAdminById("abc"));
		
	}

}
