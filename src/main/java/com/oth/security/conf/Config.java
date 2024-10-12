package com.oth.security.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {
	//**** pour password encoder il faut importer
	@Bean
	PasswordEncoder passwordEncoder() {  return new BCryptPasswordEncoder();  }
}
