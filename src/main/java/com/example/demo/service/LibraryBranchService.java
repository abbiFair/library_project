package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.LibraryBranchDAO;
import com.example.demo.domain.LibraryBranch;

@Component
public class LibraryBranchService {
	@Autowired
	LibraryBranchDAO libraryBranchDAO;
	
	public LibraryBranchService() {
		
	}

	public List<LibraryBranch> getAllBranches() {
		List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
		allBranches = libraryBranchDAO.getAllBranches();
		return allBranches;
	}

	public LibraryBranch getBranch(LibraryBranch branch) {
		return libraryBranchDAO.getBranch(branch);
	}
}
