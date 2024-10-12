package com.oth.security.service;

import com.oth.security.dao.RoleModelRepository;
import com.oth.security.dao.UserModelRepository;
import com.oth.security.entities.RoleEntity;
import com.oth.security.entities.UserEntity;
import com.oth.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {
	private final RoleModelRepository roleModelRepository;
	private final UserModelRepository userModelRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(RoleModelRepository roleModelRepository, UserModelRepository appUserRpository, PasswordEncoder passwordEncoder) {
		this.roleModelRepository = roleModelRepository;
		this.userModelRepository = appUserRpository;
		this.passwordEncoder = passwordEncoder;
	}

	@Value("${dir.Users.Files}")
	private String dirUsersFiles;

	@Override
	public List<UserEntity> findAllUsers() {
		return userModelRepository.findAll();
	}

	@Override
	public UserEntity addUser(UserEntity user, String roleName, MultipartFile file) throws IOException {
		if (user.getAppRoles() != null && !roleName.isEmpty()) {
			log.info(" Hi I'm in add roles a user");
			String[] roles = roleName.split(",");
			Consumer<String> addRoleToUserConsumer = r -> user.getAppRoles().add(roleModelRepository.findByRoleName(r));
			Stream.of(roles).forEach(addRoleToUserConsumer);
		}
		log.info("après role for add user : " + roleName + " " + user);
		if (!file.isEmpty()) {
			log.info("**** file extension not used in this cas :) ****");
			//String extention = file.getOriginalFilename().split("[.]")[1];
			log.info("******** fileName **********");
			String fileName = user.getUsername() + "_" + System.currentTimeMillis();
			log.info("add file name of object user");
			user.setPhoto(fileName);
			log.info("***** Load the file in local : {} with name : {}", dirUsersFiles, fileName);
			FilesUtil.saveFile(dirUsersFiles, fileName, file);
		}
		String pwd = user.getPassword();
		log.info("Your password : {}", pwd);
		user.setPassword(passwordEncoder.encode(pwd));
		return userModelRepository.saveAndFlush(user);
	}

	@Override
	public UserEntity addUser(UserEntity user) {
		String pwd = user.getPassword();
		log.info("***** encode the password *****");
		user.setPassword(passwordEncoder.encode(pwd));
		return userModelRepository.saveAndFlush(user);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		UserEntity appUser = userModelRepository.findByUsername(username);
		RoleEntity appRole = roleModelRepository.findByRoleName(roleName);
		appUser.getAppRoles().add(appRole);
		userModelRepository.save(appUser);
	}

	@Override
	public UserEntity findUserByUsername(String username) {
		log.info(" username : {}", username);
		return userModelRepository.findByUsername(username);
	}

	@Override
	public Page<UserEntity> findAllUsersPerPage(int page, int size) {
		return userModelRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public byte[] getPhoto(String photo) {
		return FilesUtil.getFile(dirUsersFiles, photo);
	}

	@Override
	public void deleteUserById(Long id) {
		userModelRepository.deleteById(id);
	}

}
