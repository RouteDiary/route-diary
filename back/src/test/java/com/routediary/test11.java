package com.routediary;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class test11 {

	 @Autowired
	    private SqlSessionFactory sqlSessionFactory;

	    @Test
	    public void connection_test(){
	        try(Connection con = sqlSessionFactory.openSession().getConnection()){
	            System.out.println("커넥션 성공");
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	}

}
