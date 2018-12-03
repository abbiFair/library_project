package com.example.demo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LibraryBranchMapper {
	@Select("select branchid, branchname, address from library_branch")
	List<LibraryBranch> getAllBranches();

	@Select("select branchid, branchname, address from library_branch where branchid = #{branchid}")
	LibraryBranch getBranch(LibraryBranch branch);
	
//	@Select("select branchid, branchname, address from library_branch")
//	LibraryBranch getLibraryBranch(LibraryBranch branch);
}
