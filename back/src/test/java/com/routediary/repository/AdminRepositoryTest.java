package com.routediary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Admin;
import com.routediary.exception.SelectException;

@SpringBootTest
public class AdminRepositoryTest {
  @Autowired
  AdminRepository repository;

  @Test
  void testselectAdminById() throws SelectException {
    String adminId = "1234";
    String expectedAdminPwd = "PW4321";
    Admin admin = repository.selectAdminById(adminId);
    assertEquals(adminId, admin.getAdminId());
    assertEquals(expectedAdminPwd, admin.getAdminPwd());
  }
}
