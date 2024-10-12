package com.oth;

import com.oth.dao.BookRepository;
import com.oth.entities.Author;
import com.oth.entities.Book;
import com.oth.security.entities.RoleEntity;
import com.oth.security.entities.UserEntity;
import com.oth.security.service.IRoleService;
import com.oth.security.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootStatefulApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootStatefulApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BookRepository bookRepository, IUserService iUserModel, IRoleService iRoleModel) {
		return args ->{
			Stream.of(new Book(null,"Java",new Author(null, "Othman", "BOUAZZAOUI","1_1640635111982")), new Book(null,"Angular 11",new Author(null, "Khalid", "Ahmed")),
					new Book(null,"C#",new Author(null, "Khalid", "Ahmed")),  new Book(null,"PHP",new Author(null, "Said", "Talal"))).forEach(b -> {  bookRepository.save(b);
			});

			iRoleModel.addNewRole(new RoleEntity(null, "USER"));
			iRoleModel.addNewRole(new RoleEntity(null, "ADMIN"));
			iRoleModel.addNewRole(new RoleEntity(null, "USER2"));
			iRoleModel.addNewRole(new RoleEntity(null, "ADMIN2"));

			iUserModel.addUser(new UserEntity(null, "user1", "123", null,""));
			iUserModel.addUser(new UserEntity(null, "admin", "123", null,""));
			iUserModel.addUser(new UserEntity(null, "user2", "123", null, ""));
			iUserModel.addUser(new UserEntity(null, "admin2", "123", null, ""));
			iUserModel.addRoleToUser("user1", "USER");
			iUserModel.addRoleToUser("admin", "ADMIN");
			iUserModel.addRoleToUser("user2", "USER2");
			iUserModel.addRoleToUser("admin2", "ADMIN2");
		};
	}

}