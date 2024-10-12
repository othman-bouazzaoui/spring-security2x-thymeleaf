package com.oth.web;

import java.util.List;

import com.oth.entities.Author;
import com.oth.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oth.service.IAuthorService;
import com.oth.service.IBookService;

/**
 * @author Othman BOUAZZAOUI
 **/
@Controller
public class BookController {
	@Autowired
	private IBookService bookService;
	@Autowired
	private IAuthorService authorService;

	//@RequestMapping(value = "/books", method = RequestMethod.GET)
	@GetMapping(value = "/books")
	public String findAll(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {
		//List<Book> books = bookService.findAll();
		Page<Book> books = bookService.findAllPerPage(page, size);
		modelMap.addAttribute("pages", new int[books.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		modelMap.addAttribute("books", books);
		return "books";
	}

	@GetMapping(value = "/book/addBook")
	public String addBook(ModelMap modelMap) {
		List<Author> authors = authorService.findAll();
		Book book = new Book();
		modelMap.addAttribute("authors", authors);
		modelMap.addAttribute("book", book);
		return "/book/add";
	}

	@GetMapping(value = "/book/updateBook")
	public String updateBook(ModelMap modelMap, @RequestParam("id") Long id) {
		List<Author> authors = authorService.findAll();
		Book book = bookService.bookById(id);
		modelMap.addAttribute("authors", authors);
		modelMap.addAttribute("book", book);
		return "/book/update";
	}

	@GetMapping(value = "/book/delete")
	public String deleteBook(@RequestParam("id") Long id) {
		Book b = bookService.bookById(id);
		b.setAuthor(null);
		bookService.delete(id);
		return "redirect:/books";
	}

	@PostMapping(value = "/book/saveBook")
	public String saveBook(@ModelAttribute("book") Book book, @RequestParam(name = "author", defaultValue = "0") Long id) {
		Author author = authorService.findAuthorById(id);
		book.setAuthor(author);
		bookService.add(book);
		return "redirect:/books";
	}
}

