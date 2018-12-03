package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.service.BookService;

@Component
public class BookConverter implements Converter<String, Book> {
	
	@Autowired
	BookService bookservice;
	
	@Override
	public Book convert(String bookid) {
		Book book = new Book();
		book.setBookid(bookid);
		book = bookservice.getBook(book);
		return book;
	}

}
