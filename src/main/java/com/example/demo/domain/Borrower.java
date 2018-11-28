package com.example.demo.domain;

import java.math.BigDecimal;

public class Borrower {
	private String cardno;
	private String name;
	private String address;
	private String phone;
	private String password;
	
	
	
	/**
	 * @param cardno
	 * @param name
	 * @param address
	 * @param phone
	 * @param password
	 */
	public Borrower(String cardno, String name, String address, String phone, String password) {
		super();
		this.cardno = cardno;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.password = password;
	}
	public Borrower() {
		// TODO Auto-generated constructor stub
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Borrower [cardno=" + cardno + ", name=" + name + ", address=" + address + ", phone=" + phone + ", password=" + password +"]";
	}
	
}
