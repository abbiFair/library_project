package com.example.demo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BorrowerMapper {
	@Select("SELECT cardno, name, address, phone, password FROM BORROWER")
	List<Borrower> getBorList();

	@Insert("INSERT into BORROWER (cardno, name, address, phone, password) "
			+ "VALUES(#{cardno}, #{name}, #{address}, #{phone}, #{password})")
	void insertBorrower(String name, String address, String phone, String password, String cardno);

	@Select("SELECT * FROM BORROWER WHERE cardno =#{cardno}")
	Borrower getBorrower(String cardno);

	@Select("SELECT cardno FROM BORROWER")
	List<String> getCardNumbers();

	@Update("UPDATE BORROWER set name = #{name}, address = #{address}, phone = #{phone} WHERE cardno = #{cardno}")
	void updateBorrower(Borrower bor);
}