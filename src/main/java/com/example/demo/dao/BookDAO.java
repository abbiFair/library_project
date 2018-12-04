package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookLoan;
import com.example.demo.domain.BookMapper;
import com.example.demo.domain.Borrower;
import com.example.demo.domain.LibraryBranch;

@Component
public class BookDAO {
	@Autowired
	BookMapper bookMapper;
	
	public BookDAO() {
		
	}

	public List<Book> getBookList() {
		List<Book> bookList = new ArrayList<Book>();	
		bookList = bookMapper.getBookList();
		return bookList;
	}
	
	public Book getBook(Book book) {
		return bookMapper.getBook(book);
	}

	public List<Book> getBookListByBranch(LibraryBranch branch) {
		List<Book> bookList = new ArrayList<Book>();	
		bookList = bookMapper.getBookListByBranch(branch);
		return bookList;
	}

	public void insertBookLoan(BookLoan bookloan) {
		bookMapper.insertBookLoan(bookloan);
	}
}
