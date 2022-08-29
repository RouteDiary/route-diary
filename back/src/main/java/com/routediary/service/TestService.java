//package com.routediary.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import com.routediary.repository.TestRepository;
//
//@Service
//@Transactional
//public class TestService {
//  @Autowired
//  TestRepository repository;
//
//  @Autowired
//  PlatformTransactionManager manager;
//
//  public int totSize(String str) {
//
//    TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());
//    int totSize = 0;
//    try {
//      totSize = repository.toSize("1", "1", 1);
//
//      // manager.commit(status);
//      // manager.rollback(status);
//
//    } catch (Exception ex) {
//      ex.printStackTrace();
//    }
//    return totSize;
//  }
//
//}
