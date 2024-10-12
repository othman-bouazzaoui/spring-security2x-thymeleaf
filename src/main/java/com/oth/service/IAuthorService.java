package com.oth.service;

import com.oth.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAuthorService{

		public List<Author> findAll();
		public Author add(Author author, MultipartFile file) throws Exception;
		public Author update(Author a);
		public Author delete(Long id);
		public Author findAuthorById(Long id);
		public Page<Author> findAllPerPage(int page, int size);
		public byte[] getPhoto(String photo);

}
