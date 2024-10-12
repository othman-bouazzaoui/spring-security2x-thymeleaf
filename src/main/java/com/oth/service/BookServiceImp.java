package com.oth.service;

import com.oth.dao.BookRepository;
import com.oth.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Othman BOUAZZAOUI
 */
@Service
@RequiredArgsConstructor
public class BookServiceImp implements IBookService {

	private final BookRepository bookRepository;

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book add(Book b) {
		return bookRepository.save(b);
	}

	@Override
	public Book update(Book b) {
		return bookRepository.save(b);
	}

	@Override
	public Book delete(Long id) {
		Book b = bookRepository.findById(id).orElse(null);
		bookRepository.delete(b);
		return b;
	}

	@Override
	public Book bookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Book> findAllPerPage(int page, int size) {
		return bookRepository.findAll(PageRequest.of(page, size));
	}
}
