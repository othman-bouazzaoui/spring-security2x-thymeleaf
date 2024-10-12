package com.oth.security.service;

import com.oth.security.entities.RoleEntity;
import com.oth.security.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

	private final IUserService userService;

	public UserDetailsServiceImp(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userService.findUserByUsername(username);

		Collection<GrantedAuthority> authorities = new ArrayList<>();

		Consumer<RoleEntity> roles = r -> authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		userEntity.getAppRoles().forEach(roles);
		return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
	}
}
