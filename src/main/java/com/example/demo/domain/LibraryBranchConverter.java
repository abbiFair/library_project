package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.service.LibraryBranchService;

@Component
public class LibraryBranchConverter implements Converter<String, LibraryBranch> {
	
	@Autowired
	LibraryBranchService librarybranchservice;
	
	@Override
	public LibraryBranch convert(String branchid) {
		System.out.println("Trying to convert id=" + branchid + " into a branch");
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchid(branchid);
		branch = librarybranchservice.getBranch(branch);
		return branch;
	}

}
