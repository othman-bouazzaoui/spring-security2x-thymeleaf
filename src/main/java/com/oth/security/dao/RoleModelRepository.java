package com.oth.security.dao;

import com.oth.security.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleModelRepository extends JpaRepository<RoleEntity, Long> {
	/**
	 * find RoleEntity by roleName
	 * @param roleName
	 * @return
	 */
	RoleEntity findByRoleName(String roleName);
}
