package com.oth.security.web;

import java.io.IOException;
import java.util.List;

import com.oth.security.entities.RoleEntity;
import com.oth.security.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oth.security.service.IRoleService;
import com.oth.security.service.IUserService;

@Controller
@RequestMapping(path = "/admin")
public class UserController {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IRoleService iRoleService;

	@GetMapping(value = "/users")
	public String findAllUsers(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {
		Page<UserEntity> users = iUserService.findAllUsersPerPage(page, size);
		modelMap.addAttribute("pages", new int[users.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		modelMap.addAttribute("users", users);
		return "admin/users";
	}
	@GetMapping(value = "/user/add")
	public String addPage(ModelMap modelMap) {
		List<RoleEntity> roles = iRoleService.findAllRoles();
		UserEntity user = new UserEntity();
		modelMap.addAttribute("roles", roles);
		modelMap.addAttribute("user", user);
		return "/admin/user/add";
	}

	@GetMapping(value = "/user/updateUser")
	public String updateUser( @RequestParam("username") String userName, ModelMap modelMap) {
		UserEntity user = iUserService.findUserByUsername(userName);
		List<RoleEntity> roles = iRoleService.findAllRoles();
		modelMap.addAttribute("roles", roles);
		modelMap.addAttribute("user", user);
		return "/admin/user/update";
	}

	@GetMapping(value = "/deleteUser")
	public String deleteUser( @RequestParam("id") Long id) throws InterruptedException {
		iUserService.deleteUserById(id);
		return "redirect:/admin/users";
	}

	@PostMapping(value = "/user/saveUser")
	public String saveUser(@ModelAttribute("user") UserEntity appUser,  @RequestParam("roleName") String roleName,@RequestParam( name = "image", required = false) MultipartFile file) throws IOException {
		iUserService.addUser(appUser,roleName, file);
		return "redirect:/admin/users";
	}

	@GetMapping(value = "/getFile", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(@RequestParam("photo") String photo) {
		return iUserService.getPhoto(photo);
	}
}