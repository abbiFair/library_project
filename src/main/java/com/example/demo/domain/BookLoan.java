package com.example.demo.domain;

public class BookLoan {
	private String bookid;
	private String branchid;
	private String cardno;
	
	/**
	 * @param bookid
	 * @param branchid
	 * @param cardno
	 */
	
	public BookLoan() {
		
	}
	
	public BookLoan(String bookid, String branchid, String cardno) {
		super();
		this.bookid = bookid;
		this.branchid = branchid;
		this.cardno = cardno;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	@Override
	public String toString() {
		return "BookLoan [bookid=" + bookid + ", branchid=" + branchid + ", cardno=" + cardno +  "]";
	}
	
}
