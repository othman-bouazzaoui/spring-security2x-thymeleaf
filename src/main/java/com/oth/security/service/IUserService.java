package com.oth.security.service;

import com.oth.security.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
	public UserEntity addUser(UserEntity user, String roleName, MultipartFile file) throws IOException;
	public UserEntity addUser(UserEntity user) throws IOException;
	public void addRoleToUser(String username, String roleName);
	public UserEntity findUserByUsername(String username);
	public Page<UserEntity> findAllUsersPerPage(int page, int size);
	public List<UserEntity> findAllUsers();
	public byte[] getPhoto(String photo);
	public void deleteUserById(Long id);

}
