package com.oth.security.web;

import com.oth.security.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oth.security.service.IRoleService;

@Controller
@RequestMapping(path = "/admin")
public class RoleController {
	@Autowired
	private IRoleService roleService;

	@GetMapping(value = "/roles")
	public String allRoles(ModelMap modelMap,@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "3") int size) {
		Page<RoleEntity> appRole = roleService.findAllRolesPerPage(page, size);
		modelMap.addAttribute("pages", new int[appRole.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		modelMap.addAttribute("roles", appRole);
		return "/admin/roles";
	}
	@GetMapping(value = "/role/add")
	public String addPage() {
		return "/admin/role/add";
	}

	@PostMapping(value = "/role/addRole")
	public String addRole( @RequestParam("roleName") String roleName) throws InterruptedException {
		roleService.addNewRole(new RoleEntity(null,roleName.toString()));
		return "redirect:/admin/roles";
	}
	@GetMapping(value = "/role/updateRole")
	public String updateRole( @RequestParam("roleName") String roleName, ModelMap modelMap) throws InterruptedException {
		RoleEntity role = roleService.findRoleByName(roleName);
		modelMap.addAttribute("role", role);
		return "/admin/role/update";
	}

	@GetMapping(value = "/deleteRole")
	public String deleteRole(@RequestParam("id") Long id) {
		roleService.deleteRoleById(id);
		return "redirect:/admin/roles";
	}

	@PostMapping(value = "/role/saveRole")
	public String saveRole(@ModelAttribute("role") RoleEntity appRole ) {
		roleService.addNewRole(appRole);
		return "redirect:/admin/roles";
	}

}
