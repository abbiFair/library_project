package com.example.demo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BorrowerMapper {
	@Select("SELECT cardno, name, address, phone, password FROM BORROWER")
	List<Borrower> getBorList();
}