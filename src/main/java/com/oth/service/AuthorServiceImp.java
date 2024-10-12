package com.oth.service;

import java.util.List;

import com.oth.dao.AuthorRepository;
import com.oth.entities.Author;
import com.oth.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/*
	@author Othman BOUAZZAOUI
*/
@Slf4j
@Service
public class AuthorServiceImp implements IAuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Value("${dir.Authors.Files}")
	private String dirAuthorsFiles;

	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	@Override
	public Author add(Author author, MultipartFile file) throws Exception {
		authorRepository.save(author);
		if (!file.isEmpty()) {
			try {
				log.info("**** file extension not used in this cas :) ****");
				String fileName = author.getId().toString() + "_" + System.currentTimeMillis();
				author.setPhoto(fileName);
				log.info("***** Load the file in local : " + dirAuthorsFiles + " with name : " + fileName);
				FilesUtil.saveFile(dirAuthorsFiles, fileName, file);
			} catch (Exception e) {
				log.error(e.getMessage());
				throw e;
			}
		}
		return authorRepository.save(author);
	}

	@Override
	public Author update(Author a) {
		return authorRepository.save(a);
	}

	@Override
	public Author delete(Long id) {
		Author author = authorRepository.findById(id).orElse(null);
		authorRepository.delete(author);
		return author;
	}

	@Override
	public Author findAuthorById(Long id) {
		return authorRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Author> findAllPerPage(int page, int size) {
		return authorRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public byte[] getPhoto(String photo) {
		return FilesUtil.getFile(dirAuthorsFiles, photo);
	}
}
