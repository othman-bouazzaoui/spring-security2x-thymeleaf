package com.oth.security.dao;

import com.oth.security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepository extends JpaRepository<UserEntity, Long> {
	/**
	 * find UserEntity By username
	 * @param username
	 * @return
	 */
	UserEntity findByUsername(String username);
}
