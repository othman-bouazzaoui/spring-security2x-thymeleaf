package com.oth.security;

import com.oth.security.service.UserDetailsServiceImp;
import com.oth.security.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImp userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// csrf activated because we manage security with stateful mode, but we should ignore it for h2-console connection
		http.csrf().ignoringAntMatchers("/h2-console/**");

		/*
		 1 - header frameOptions deactivated for h2-console
		 2 - activate security for admin part
		 3 - authorized paths
		 4 - Login and accessDenied pages
		 */
		//1-
		http.headers().frameOptions().disable().and()
				//2-
				.authorizeRequests().antMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ADMIN").and()
				//3-
				.authorizeRequests().antMatchers("/h2-console/**", "/login/**", "/", "/images/**", "/bootstrap/**", "/common/**").permitAll().and()
				.authorizeRequests().anyRequest().authenticated().and()
				//4-
				.formLogin().loginPage("/login").defaultSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/accessDenied");
	}


}
