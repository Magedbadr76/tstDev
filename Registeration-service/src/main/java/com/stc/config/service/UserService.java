package com.stc.config.service;

import java.util.List;

import com.stc.config.entity.User;
import com.stc.config.repository.RegisterRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	RegisterRepo registerRepo;
	
	public String register( User user)
	{
		User savedUser =registerRepo.save(user);
		return "The user: "+savedUser.getName() + " registered successfully";
	}
	
	public User createUser( User user)
	{
		User savedUser =registerRepo.save(user);
		return savedUser;
	}
	
	public List<User> findAllUsers()
	{
		return registerRepo.findAll();
	}
	
	
	public User findUser( String email)
	{
		return registerRepo.findByEmail(email).get();
	}
	
	public List<User> cancelTrgisteration( int id)
	{
		registerRepo.deleteById(id);
		return registerRepo.findAll();
	}
	
	public void deleteUser(int id)
	{
		registerRepo.deleteById(id);
	}

	public Page<User> findAllUsersByPages(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		
		return  registerRepo.findAll(pageRequest);
	}
	
	public List<User> findAllUsersByPagesSorted(int page, int size) {
		Sort sort = Sort.by("name").ascending();
		PageRequest pageRequest = PageRequest.of(page, size,sort);
		
		return  registerRepo.findAll(pageRequest).getContent();
	}
	public List<User> findAllUsersByList(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		
		return  registerRepo.findAll(pageRequest).getContent();
	}
}
