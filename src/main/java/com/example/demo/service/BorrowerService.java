package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.BorrowerDAO;
import com.example.demo.domain.Borrower;


@Component
public class BorrowerService {
	
	@Autowired
	BorrowerDAO borDAO;
	
	public BorrowerService() {
		
	}

	public List<Borrower> getBorList() {
		List<Borrower> borList = new ArrayList<Borrower>();
		//EmployeeDAO empDAO = new EmployeeDAO();
		borList = borDAO.getBorList();
		return borList;
	}
	
	public void insertBorrower(Borrower borrower) {
		borDAO.insertBorrower(borrower);
	}

	public Borrower getBorrower(String cardno) {
		return borDAO.getBorrower(cardno);
	}

}
