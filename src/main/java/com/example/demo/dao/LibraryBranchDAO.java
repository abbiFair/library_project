package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.LibraryBranch;
import com.example.demo.domain.LibraryBranchMapper;

@Component
public class LibraryBranchDAO {
	@Autowired
	LibraryBranchMapper libraryBranchMapper;
	
	public LibraryBranchDAO() {
		
	}

	public List<LibraryBranch> getAllBranches() {
		List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();	
		allBranches = libraryBranchMapper.getAllBranches();
		return allBranches;
	}

	public LibraryBranch getBranch(LibraryBranch branch) {
		return libraryBranchMapper.getBranch(branch);
	}
}
