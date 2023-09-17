package com.stc.config.controller;

import java.util.List;

import com.stc.config.entity.User;
import com.stc.config.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

@Autowired
UserService userService;
	
	@PostMapping("/register")
	public String register(@RequestBody User user)
	{
		userService.register(user);
		return "The user: "+user.getName() + " registered successfully";
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		User u = userService.createUser(user);
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllUsers")
	public List<User> findAllUsers()
	{
		return userService.findAllUsers();
	}
	
	@GetMapping("/users")
	public Page<User> findAllUsersByPages(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "10") int size )
	{
		return userService.findAllUsersByPages(page , size);
	}
	@GetMapping("/usersList")
	public List<User> findAllUsersByList(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "10") int size )
	{
		return userService.findAllUsersByList(page , size);
	}
	
	@GetMapping("/usersListSorted")
	public List<User> findAllUsersByPagesSortedByName(@RequestParam(defaultValue = "0") int page ,@RequestParam(defaultValue = "10") int size )
	{
		return userService.findAllUsersByPagesSorted(page , size);
	}
	
	@GetMapping("/findUser/{email}")
	public User findUser(@PathVariable String email)
	{
		return userService.findUser(email);
	}
	
	@GetMapping("/cancel/{id}")
	public List<User> cancelTrgisteration(@PathVariable int id)
	{
		
		return userService.cancelTrgisteration(id);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public void deleteUser(@PathVariable int id)
	{
		
		 userService.deleteUser(id);
	}
}
