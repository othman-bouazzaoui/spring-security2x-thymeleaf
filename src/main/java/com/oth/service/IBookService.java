package com.oth.service;

import com.oth.entities.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBookService {

	public List<Book> findAll();

	public Book add(Book b);

	public Book update(Book b);

	public Book delete(Long id);

	public Book bookById(Long id);

	public Page<Book> findAllPerPage(int page, int size);

}
