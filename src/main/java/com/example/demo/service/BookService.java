package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.BookDAO;
import com.example.demo.domain.Book;
import com.example.demo.domain.BookLoan;
import com.example.demo.domain.Borrower;
import com.example.demo.domain.LibraryBranch;

@Component
public class BookService {
	@Autowired
	BookDAO bookDAO;
	
	public BookService() {
		
	}

	public List<Book> getBookList() {
		List<Book> bookList = new ArrayList<Book>();
		bookList = bookDAO.getBookList();
		return bookList;
	}
	
	public Book getBook(Book book) {
		return bookDAO.getBook(book);
	}

	public List<Book> getBookListByBranch(LibraryBranch branch) {
		List<Book> bookList = new ArrayList<Book>();
		bookList = bookDAO.getBookListByBranch(branch);
		return bookList;
	}

	public void insertBookLoan(BookLoan bookloan) {
		bookDAO.insertBookLoan(bookloan);
	}
}
