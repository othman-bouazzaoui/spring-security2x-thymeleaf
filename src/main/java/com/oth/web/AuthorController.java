package com.oth.web;

import java.io.IOException;

import com.oth.entities.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oth.service.IAuthorService;

/** @author Othman BOUAZZAOUI **/
@Controller
public class AuthorController {
	@Autowired
	private IAuthorService authorService;

	@GetMapping(value = "/authors")
	public String findAll(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {
		//List<Author> authors = authorService.findAll();
		Page<Author> authors = authorService.findAllPerPage(page, size);
		modelMap.addAttribute("pages", new int[authors.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		modelMap.addAttribute("authors", authors);
		return "authors";
	}

	@GetMapping(value = "/author/add")
	public String addAuthor(ModelMap modelMap) {
		Author author = new Author();
		modelMap.addAttribute("author", author);
		return "/author/add";
	}

	@GetMapping(value = "/author/update")
	public String updateAuthor(@RequestParam(name = "id") Long id, ModelMap modelMap) throws IllegalStateException, IOException {
		Author author = authorService.findAuthorById(id);
		modelMap.addAttribute("author", author);
		return "/author/update";
	}

	@PostMapping("/author/saveAuthor")
	public String saveAuthor(@ModelAttribute(name = "author") Author author, @RequestParam(name = "image", required = false) MultipartFile file)
			throws Exception {
		authorService.add(author, file);
		return "redirect:/authors";
	}

	@GetMapping(value = "/author/delete")
	public String deleteAuhtor(@RequestParam(name = "id") Long id) {
		authorService.delete(id);
		return "redirect:/authors";
	}

	@GetMapping(value = "/getFile", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(@RequestParam("photo") String photo) {
		return authorService.getPhoto(photo);
	}
}

