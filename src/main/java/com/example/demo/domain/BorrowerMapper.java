package com.example.demo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BorrowerMapper {
	@Select("SELECT cardno, name, address, phone, password FROM BORROWER")
	List<Borrower> getBorList();

	@Insert("insert into borrower .... ")
	void insertBorrower(Borrower borrower);

	@Select("SELECT * FROM BORROWER WHERE cardno =#{cardno}")
	Borrower getBorrower(String cardno);

	@Select("SELECT cardno FROM BORROWER")
	List<String> getCardNumbers();
}