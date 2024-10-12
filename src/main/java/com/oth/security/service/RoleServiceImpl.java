package com.oth.security.service;

import com.oth.security.dao.RoleModelRepository;
import com.oth.security.dao.UserModelRepository;
import com.oth.security.entities.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

	private final RoleModelRepository roleModelRepository;
	private final UserModelRepository userModelRepository;

	public RoleServiceImpl(RoleModelRepository roleModelRepository, UserModelRepository userModelRepository) {
		this.roleModelRepository = roleModelRepository;
		this.userModelRepository = userModelRepository;
	}

	@Override
	public RoleEntity addNewRole(RoleEntity role) {
		return roleModelRepository.save(role);
	}

	@Override
	public List<RoleEntity> findAllRoles() {
		return roleModelRepository.findAll();
	}

	@Override
	public Page<RoleEntity> findAllRolesPerPage(int page, int size) {
		return roleModelRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public RoleEntity findRoleByName(String roleName) {
		return roleModelRepository.findByRoleName(roleName);
	}

	@Override
	public RoleEntity findRoleById(Long id) {
		return roleModelRepository.getById(id);
	}

	@Override
	public void deleteRoleById(Long id) {
		RoleEntity appRole = this.findRoleById(id);
		userModelRepository.findAll().forEach(a -> a.getAppRoles().remove(appRole));
		roleModelRepository.deleteById(id);
	}

}

