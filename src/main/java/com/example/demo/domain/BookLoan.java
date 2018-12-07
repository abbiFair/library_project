package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class BookLoan {
	private String bookid;
	private String branchid;
	private String cardno;
	private String duedate;
	private String title;
	private String rating;
	
	
	/**
	 * @param bookid
	 * @param branchid
	 * @param cardno
	 * @param duedate
	 * @param title
	 * @param rating
	 */
	
	public BookLoan() {
		
	}
	
	public BookLoan(String bookid, String branchid, String cardno, String duedate,
			String title, String rating) {
		super();
		this.bookid = bookid;
		this.branchid = branchid;
		this.cardno = cardno;
		this.duedate = duedate;
		this.title = title;
		this.rating = rating;
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
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "BookLoan [bookid=" + bookid + ", branchid=" + branchid + ", cardno=" + cardno +  ", duedate=" + duedate +  " , title=" + title +  "]";
	}
	
}
