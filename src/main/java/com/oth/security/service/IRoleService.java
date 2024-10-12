package com.oth.security.service;

import com.oth.security.entities.RoleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {
	public RoleEntity addNewRole(RoleEntity role);
	public List<RoleEntity> findAllRoles();
	public Page<RoleEntity> findAllRolesPerPage(int page, int size);
	public RoleEntity findRoleByName(String roleName);
	public RoleEntity findRoleById(Long id);
	public void deleteRoleById(Long id);

}
