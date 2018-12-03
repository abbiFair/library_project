package com.example.demo.domain;

public class Book {
	private String bookid;
	private String title;
	private String publisher;
	private String numberavailable;
	
	/**
	 * @param bookid
	 * @param title
	 * @param publisher
	 * @param numberavailable
	 */
	
	public Book() {
		
	}
	
	public Book(String bookid, String title, String publisher, String numberavailable) {
		super();
		this.bookid = bookid;
		this.title = title;
		this.publisher = publisher;
		this.numberavailable = numberavailable;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getNumberavailable() {
		return numberavailable;
	}
	public void setNumberavailable(String numberavailable) {
		this.numberavailable = numberavailable;
	}
	@Override
	public String toString() {
		return "Book [bookid=" + bookid + ", title=" + title + ", publisher=" + publisher + ", numberavailable=" + numberavailable + "]";
	}
	
}
