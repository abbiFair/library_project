package com.example.demo.domain;

public class LibraryBranch {
	private String branchid;
	private String branchname;
	private String address;	
	
	/**
	 * @param branchid
	 * @param branchname
	 * @param address
	 */
	public LibraryBranch(String branchid, String branchname, String address) {
		super();
		this.branchid = branchid;
		this.branchname = branchname;
		this.address = address;
	}
	public LibraryBranch() {
		// TODO Auto-generated constructor stub
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Borrower [branchid=" + branchid + ", branchname=" + branchname + ", address=" + address + "]";
	}
	
}