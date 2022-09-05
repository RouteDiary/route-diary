package com.routediary.repository;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.routediary.dto.Admin;

@SpringBootTest
public class AdminRepositoryTest {
  @Autowired
  AdminRepository repository;

  @Test
  void testselectAdminById() throws Exception {
    String adminId = "1234";
    String expectedAdminPwd = "PW4321";
    Admin admin = repository.selectAdminById(adminId);
    assertEquals(adminId, admin.getAdminId());
    assertEquals(expectedAdminPwd, admin.getAdminPwd());
  }
}
