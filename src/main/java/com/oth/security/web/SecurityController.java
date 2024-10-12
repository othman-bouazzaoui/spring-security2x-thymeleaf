package com.oth.security.web;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class SecurityController {

	@GetMapping(value = "/accesDenied")
	public String accesDenied() {
		return "accesDenied";
	}

	@GetMapping(value = "/login")
	public String login(HttpServletRequest request) {
		return "/login";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/login";
	}


}