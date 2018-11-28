package com.example.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Borrower;
import com.example.demo.domain.BorrowerMapper;


@Component
public class BorrowerDAO {
	
	@Autowired
	BorrowerMapper borrowerMapper;
	
	public BorrowerDAO() {
		
	}

	public List<Borrower> getBorList() {
		List<Borrower> borList = new ArrayList<Borrower>();	
		borList = borrowerMapper.getBorList();
		//Employee e1 = new Employee("Test1","Test1 LName","1234",new BigDecimal(10000.00));
		//Employee e2 = new Employee("Test2","Test2 LName","2345",new BigDecimal(20000.00));
		//empList.add(e1);
		//empList.add(e2);
		return borList;
	}


	public void insertBorrower(Borrower borrower) {
	}
	
}
