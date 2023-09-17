package com.stc.config.repository;

import java.util.Optional;

import com.stc.config.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<User, Integer> {

	public Optional<User> findByEmail(String email);

}
